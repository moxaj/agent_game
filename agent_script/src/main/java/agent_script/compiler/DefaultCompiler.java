package agent_script.compiler;

import agent_script.ResourceUtils;
import agent_script.compiler.analyzer.Symbol;
import agent_script.compiler.analyzer.namespace.NamespaceAnalyzer;
import agent_script.compiler.analyzer.namespace.NamespaceDefinition;
import agent_script.compiler.analyzer.native_meta.NativeMetaAnalyzer;
import agent_script.compiler.analyzer.reference.ReferenceAnalyzer;
import agent_script.compiler.analyzer.return_statement.ReturnStatementAnalyzer;
import agent_script.compiler.analyzer.type.TypeAnalyzer;
import agent_script.compiler.emitter.Emitter;
import agent_script.compiler.java_compiler.JavaCompiler;
import agent_script.compiler.parser.Parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static agent_script.compiler.CompilerMessageTemplates.*;

/**
 * Default {@link Compiler} implementation.
 */
public final class DefaultCompiler implements Compiler {
    /**
     * The file extension of the script files.
     */
    public static final String SCRIPT_FILE_EXTENSION = ".as";

    /**
     * The current version of the compiler, used as a part of the cache key.
     */
    private static final String COMPILER_VERSION = "0001";

    /**
     * The root source path of the bundled sources.
     */
    private static final String MAIN_ROOT_SOURCE_PATH_STR = "/agent_script/compiler/main";

    /**
     * The message reporter.
     */
    private final CompilerMessageReporter messageReporter;

    /**
     * The compiler cache directory.
     * TODO reimplement caching
     */
    private final Path cacheDirectory;

    /**
     * The collected compiler diagnostics.
     */
    private final CompilerDiagnostics diagnostics = new CompilerDiagnostics();

    public DefaultCompiler(Logger logger, Path cacheDirectory) {
        messageReporter = new CompilerMessageReporter(logger);
        this.cacheDirectory = cacheDirectory;

        if (cacheDirectory != null) {
            try {
                Files.createDirectories(cacheDirectory);
            } catch (IOException e) {
                messageReporter.report(C_0005.render(null, cacheDirectory.toAbsolutePath().toString()));
                throw new CompilerException(e);
            }
        }
    }

    /**
     * Returns a list of namespace names in reverse dependency order, or throws if there's a circular dependency.
     *
     * @param namespaceBundles the namespace bundles
     * @return the ordered namespace names
     */
    private List<NamespaceBundle> orderedNamespaceBundles(Map<Symbol, NamespaceBundle> namespaceBundles) {
        Set<Symbol> orderedNamespaceNames = new LinkedHashSet<>();

        Map<Symbol, NamespaceBundle> remainingNamespaceBundles = new HashMap<>(namespaceBundles);
        while (!remainingNamespaceBundles.isEmpty()) {
            Symbol namespaceName = remainingNamespaceBundles.entrySet().stream()
                    .filter(entry -> orderedNamespaceNames.containsAll(
                            entry.getValue().getNamespaceDefinition().getImportedNamespaceNames().values()))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElseThrow(() -> {
                        messageReporter.report(C_0002.render(null, remainingNamespaceBundles.keySet().stream()
                                .map(name -> String.format("'%s'", name))
                                .collect(Collectors.joining(", "))));
                        return new CompilerException();
                    });
            remainingNamespaceBundles.remove(namespaceName);
            orderedNamespaceNames.add(namespaceName);
        }

        return orderedNamespaceNames.stream().map(namespaceBundles::get).collect(Collectors.toList());
    }

    /**
     * Computes a cache key for a namespace bundle. Uses the name of the namespace, the current compiler
     * version, and the hash of the source.
     *
     * @param namespaceBundle the namespace bundle
     * @return the computed cache key
     */
    private String toCacheKey(NamespaceBundle namespaceBundle) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        messageDigest.update(namespaceBundle.getSource().getBytes());
        return String.format("%s_%s_%s.class",
                String.join("_", namespaceBundle.getNamespaceDefinition().getName().getNamespaceFragments()),
                COMPILER_VERSION,
                Base64.getUrlEncoder().encodeToString(messageDigest.digest()));
    }

    /**
     * The implementation of {@link #compile(Path[])}
     */
    private Map<Symbol, Class<?>> compile0(Path[] rootSourcePaths) throws CompilerException {
        messageReporter.report(C_0003.render(null, Arrays.stream(rootSourcePaths)
                .map(rootSourcePath -> String.format("'%s'", rootSourcePath.toString()))
                .collect(Collectors.joining(", "))));

        Map<Symbol, NamespaceDefinition> namespaceDefinitions = new HashMap<>();
        Map<Symbol, NamespaceBundle> namespaceBundles = Arrays.stream(rootSourcePaths)
                .flatMap(rootSourcePath -> {
                    try {
                        return Files.walk(rootSourcePath)
                                .filter(sourcePath -> sourcePath.toString().endsWith(DefaultCompiler.SCRIPT_FILE_EXTENSION))
                                .map(sourcePath -> {
                                    NamespaceBundle namespaceBundle = new NamespaceBundle(rootSourcePath, sourcePath);

                                    // Parse it
                                    new Parser(messageReporter).process(namespaceBundle, namespaceDefinitions);

                                    // First analysis pass: establish top level elements and relations
                                    new NamespaceAnalyzer(messageReporter).process(namespaceBundle, namespaceDefinitions);

                                    NamespaceDefinition namespaceDefinition = namespaceBundle.getNamespaceDefinition();
                                    if (namespaceDefinitions.put(namespaceDefinition.getName(), namespaceDefinition) != null) {
                                        messageReporter.report(C_0001.render(namespaceDefinition.getLocation(), namespaceDefinition
                                                .getName()));
                                        throw new CompilerException();
                                    }

                                    return namespaceBundle;
                                });
                    } catch (IOException e) {
                        messageReporter.report(C_0000.render(null, rootSourcePath.toAbsolutePath().toString()));
                        throw new CompilerException(e);
                    }
                })
                .collect(Collectors.toMap(namespaceBundle -> namespaceBundle.getNamespaceDefinition().getName(), Function.identity()));

        // Second analysis pass: various checks
        namespaceBundles.values().forEach(namespaceBundle -> {
            new NativeMetaAnalyzer(messageReporter).process(namespaceBundle, namespaceDefinitions);
            new ReturnStatementAnalyzer(messageReporter).process(namespaceBundle, namespaceDefinitions);
            new ReferenceAnalyzer(messageReporter).process(namespaceBundle, namespaceDefinitions);
            new TypeAnalyzer(messageReporter).process(namespaceBundle, namespaceDefinitions);
        });

        // If any errors occurred, terminate here
        int errorCount = (int) messageReporter.getMessages().stream()
                .filter(compilerMessage -> compilerMessage.getLevel() == CompilerMessageTemplate.Level.ERROR)
                .count();
        diagnostics.setErrorCount(errorCount);
        if (errorCount > 0) {
            throw new CompilerException();
        }

        // Emit java sources
        namespaceBundles.values().forEach(namespaceBundle -> new Emitter(messageReporter).process(namespaceBundle, namespaceDefinitions));

        // Compile them
        JavaCompiler javaCompiler = new JavaCompiler(messageReporter);
        return orderedNamespaceBundles(namespaceBundles).stream().collect(Collectors.toMap(
                namespaceBundle -> namespaceBundle.getNamespaceDefinition().getName(),
                namespaceBundle -> {
                    javaCompiler.process(namespaceBundle, namespaceDefinitions);
                    return namespaceBundle.getJavaClass();
                }));
    }

    @Override
    public Map<Symbol, Class<?>> compile(Path[] rootSourcePaths) throws CompilerException {
        long startTime = System.currentTimeMillis();
        try {
            return ResourceUtils.withResourcePath(DefaultCompiler.class, MAIN_ROOT_SOURCE_PATH_STR, mainRootSourcePath ->
                    compile0(Stream.concat(Arrays.stream(rootSourcePaths), Stream.of(mainRootSourcePath)).toArray(Path[]::new)));
        } catch (CompilerException e) {
            // Fatal error
            diagnostics.setErrorCount(1);
            throw e;
        } catch (URISyntaxException e) {
            // Should not happen
            throw new RuntimeException(e);
        } finally {
            long elapsedTime = System.currentTimeMillis() - startTime;
            messageReporter.report(C_0004.render(null, diagnostics.getErrorCount(), ((double) elapsedTime) / 1000));
            diagnostics.setElapsedTime(elapsedTime);
            diagnostics.getMessages().addAll(messageReporter.getMessages());
        }
    }

    @Override
    public CompilerDiagnostics getDiagnostics() {
        return diagnostics;
    }
}
