package agent_script.compiler.java_compiler;

import agent_script.compiler.*;
import agent_script.compiler.analyzer.namespace.NamespaceDefinition;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A wrapper around the standard java compiler which also loads the class.
 */
public final class JavaCompiler extends CompilerProcessor {
    /**
     * The temporary directory where the .java and .class files reside.
     */
    private final Path tempDirectory;

    public JavaCompiler(CompilerMessageReporter messageReporter) {
        super(messageReporter);

        try {
            tempDirectory = Files.createTempDirectory("temp");
        } catch (IOException e) {
            messageReporter.report(CompilerMessageTemplates.J_0000.render(null));
            throw new CompilerException();
        }

        try {
            addURL(tempDirectory.toFile().toURI().toURL());
        } catch (MalformedURLException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.walk(tempDirectory)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(file -> {
                            if (!file.delete()) {
                                reportMessage(CompilerMessageTemplates.J_0001.render(null, file.getAbsolutePath()));
                            }
                        });
            } catch (IOException e) {
                reportMessage(CompilerMessageTemplates.J_0002.render(null, tempDirectory.toAbsolutePath().toString()));
            }
        }));
    }

    /**
     * Adds an URL to the classpath.
     *
     * @param url the url to add
     */
    private void addURL(URL url) {
        // Hack: since URLClassLoader.addURL is protected, we need reflection to access it

        // Find the method
        Method method;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
        } catch (NoSuchMethodException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        // Set it accessible
        method.setAccessible(true);

        // Invoke it
        try {
            method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{url});
        } catch (IllegalAccessException | InvocationTargetException e) {
            // Should not happen
            throw new RuntimeException(e);
        }
    }

    @Override
    public void process(NamespaceBundle namespaceBundle) throws CompilerException {
        this.namespaceBundle = namespaceBundle;

        NamespaceDefinition namespaceDefinition = namespaceBundle.getNamespaceDefinition();
        String javaPackageName = namespaceDefinition.getName().getJavaPackageName();
        String javaClassName = namespaceDefinition.getName().getJavaClassName();
        String qualifiedJavaClassName = javaPackageName + "." + javaClassName;
        Path javaSourcePath = tempDirectory.resolve(javaPackageName.replaceAll("\\.", "/")).resolve(javaClassName + ".java");
        Path javaClassPath = javaSourcePath.getParent().resolve(javaClassName + ".class");

        namespaceBundle.setJavaClassPath(javaClassPath);

        if (namespaceBundle.isCached()) {
            // Copy the class file to the temp dir
            Path cachedJavaClasspath = namespaceBundle.getCachedJavaClasspath();
            try {
                Files.createDirectories(javaClassPath.getParent());
                Files.copy(cachedJavaClasspath, javaClassPath);

                try {
                    namespaceBundle.setJavaClass(Class.forName(qualifiedJavaClassName));
                } catch (ClassNotFoundException e) {
                    // Should not happen
                    throw new RuntimeException(e);
                }

                return;
            } catch (IOException e) {
                reportMessage(CompilerMessageTemplates.J_0003.render(
                        null, cachedJavaClasspath.toAbsolutePath().toString(), javaClassPath.toAbsolutePath().toString()));
                throw new CompilerException(e);
            }
        }

        // Create the java source file
        try {
            File javaSourceFile = javaSourcePath.toFile();
            if (!javaSourceFile.exists()) {
                File parent = javaSourceFile.getParentFile();
                if (!parent.exists() && !parent.mkdirs()) {
                    throw new IOException();
                }

                if (!javaSourceFile.createNewFile()) {
                    throw new IOException();
                }
            }
        } catch (IOException e) {
            reportMessage(CompilerMessageTemplates.J_0004.render(null, javaSourcePath.toAbsolutePath().toString()));
            throw new CompilerException(e);
        }

        // Write its contents
        try (PrintWriter printWriter = new PrintWriter(javaSourcePath.toFile())) {
            printWriter.print(namespaceBundle.getJavaSource());
        } catch (FileNotFoundException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        // Compile it
        javax.tools.JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, null);
        List<String> options = Arrays.asList(
                "-classpath",
                System.getProperty("java.class.path") + ";" + tempDirectory.toAbsolutePath().toFile().toString());
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(
                Collections.singletonList(javaSourcePath.toFile()));
        if (!javaCompiler.getTask(null, fileManager, null, options, null, compilationUnits).call()) {
            // Should not happen
            throw new CompilerException();
        }

        // Load it
        try {
            namespaceBundle.setJavaClass(ClassLoader.getSystemClassLoader().loadClass(qualifiedJavaClassName));
        } catch (ClassNotFoundException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

    }
}
