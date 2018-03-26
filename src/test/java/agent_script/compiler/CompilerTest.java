package agent_script.compiler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Compiler tests.
 */
@RunWith(Parameterized.class)
public class CompilerTest {
    /**
     * The anonymous logger instance.
     */
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    /**
     * Ignored test cases.
     */
    private static final Set<CompilerMessageTemplate> IGNORED_TEST_CASES =
            new HashSet<>(Arrays.asList(
                    CompilerMessageTemplates.C_0000,
                    CompilerMessageTemplates.P_0000,
                    CompilerMessageTemplates.J_0000,
                    CompilerMessageTemplates.J_0001,
                    CompilerMessageTemplates.J_0002,
                    CompilerMessageTemplates.J_0003,
                    CompilerMessageTemplates.J_0004));

    static {
        LOGGER.setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord logRecord) {
                return String.format("[%1$tF %1$tT] [%2$-7s] %3$s %n",
                        new Date(logRecord.getMillis()),
                        logRecord.getLevel().getLocalizedName(),
                        logRecord.getMessage()
                );
            }
        });
        LOGGER.addHandler(consoleHandler);
    }

    /**
     * The root source paths for the compiler.
     */
    private final Path[] rootSourcePaths;
    /**
     * The compiler message id expected to be thrown.
     */
    private final String expectedCompilerMessageId;
    /**
     * The compiler cache directory.
     */
    private final Path cacheDirectory;
    /**
     * The compiler.
     */
    private ICompiler compiler;

    public CompilerTest(CompilerMessageTemplate expectedCompilerMessageTemplate)
            throws URISyntaxException, IOException {
        try (DirectoryStream<Path> compilerPath = Files.newDirectoryStream(
                Paths.get(ClassLoader.getSystemResource("compiler").toURI()),
                path -> path.getFileName().toString().startsWith("test_" + expectedCompilerMessageTemplate.getId()))) {
            this.rootSourcePaths = Stream.concat(
                    Stream.of(Paths.get(ClassLoader.getSystemResource("main").toURI())),
                    StreamSupport.stream(compilerPath.spliterator(), false))
                    .toArray(Path[]::new);
        }
        this.expectedCompilerMessageId = expectedCompilerMessageTemplate.getId();
        this.cacheDirectory = Paths.get(".as_cache");
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> parameters() {
        return Arrays.stream(CompilerMessageTemplates.class.getFields())
                .filter(field -> field.getType().equals(CompilerMessageTemplate.class))
                .map(field -> {
                    try {
                        return (CompilerMessageTemplate) field.get(null);
                    } catch (IllegalAccessException e) {
                        // Should not happen
                        throw new RuntimeException(e);
                    }
                })
                .filter(compilerMessageTemplate -> !IGNORED_TEST_CASES.contains(compilerMessageTemplate))
                .filter(compilerMessageTemplate -> {
                    CompilerMessageTemplate.Level level = compilerMessageTemplate.getLevel();
                    return level == CompilerMessageTemplate.Level.WARNING || level == CompilerMessageTemplate.Level.ERROR;
                })
                .map(compilerMessageTemplate -> new Object[]{compilerMessageTemplate})
                .collect(Collectors.toList());
    }

    @Before
    public void before() {
        // A fresh instance before each test just to be sure
        compiler = new Compiler(LOGGER, cacheDirectory);
    }

    @Test
    public void test() {
        try {
            compiler.compile(rootSourcePaths);
        } catch (CompilerException e) {
            // Ignore
        }

        Assert.assertTrue(
                compiler.getDiagnostics().getMessages().stream().map(CompilerMessage::getId).anyMatch(expectedCompilerMessageId::equals));
    }
}
