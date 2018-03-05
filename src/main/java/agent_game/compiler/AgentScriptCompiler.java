package agent_game.compiler;

import agent_game.antlr.AgentScriptLexer;
import agent_game.antlr.AgentScriptParser;
import agent_game.script.IAgentScript;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import javax.tools.JavaCompiler;
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
import java.util.Collections;

/**
 * {@link IAgentScriptCompiler} implementation.
 */
public class AgentScriptCompiler implements IAgentScriptCompiler {
    /**
     * The package name of the generated classes.
     */
    private static final String JAVA_PACKAGE_NAME = "agent_game";

    /**
     * The temporary directory to hold the generated .java and .class files.
     */
    private final Path tempDirectoryPath;

    /**
     * The package directory.
     */
    private final Path packagePath;

    public AgentScriptCompiler() throws IOException {
        try {
            tempDirectoryPath = Files.createTempDirectory("temp");
        } catch (IOException e) {
            throw new IOException("Could not create a necessary temporary directory.", e);
        }

        tempDirectoryPath.toFile().deleteOnExit();

        packagePath = tempDirectoryPath.resolve(JAVA_PACKAGE_NAME);
        if (!packagePath.toFile().mkdirs()) {
            throw new IOException("Could not create a necessary temporary directory.");
        }

        packagePath.toFile().deleteOnExit();
    }

    /**
     * Adds an url to the classpath.
     *
     * @param url the url to add to the classpath
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

    /**
     * Compiles the agent script source to java.
     *
     * @param agentScriptSource the agent script source
     * @return the java source
     */
    private String compileAgentScriptSource(String javaClassName, String agentScriptSource)
            throws AgentScriptCompilerException {
        // TODO error handling
        AgentScriptLexer lexer = new AgentScriptLexer(CharStreams.fromString(agentScriptSource));
        AgentScriptParser parser = new AgentScriptParser(new CommonTokenStream(lexer));
        return new AgentScriptEmitterVisitor(JAVA_PACKAGE_NAME, javaClassName).visit(parser.script());
    }

    /**
     * Compiles the java source code.
     *
     * @param javaClassName the java class name
     * @param javaSource    the java source
     * @return the loaded class
     */
    private Class<? extends IAgentScript> compileJavaSource(String javaClassName, String javaSource)
            throws AgentScriptCompilerException {
        // Create the java source file
        Path javaSourcePath = packagePath.resolve(javaClassName + ".java");
        try {
            File javaSourceFile = javaSourcePath.toFile();
            assert javaSourceFile.exists() || javaSourceFile.createNewFile();
            javaSourceFile.deleteOnExit();
        } catch (IOException e) {
            throw new AgentScriptCompilerException("Could not create a necessary temporary file.", e);
        }

        // Write its contents
        try (PrintWriter printWriter = new PrintWriter(javaSourcePath.toFile())) {
            printWriter.print(javaSource);
        } catch (FileNotFoundException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        // Compile it
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Collections
                .singletonList(javaSourcePath.toFile()));
        if (!javaCompiler.getTask(null, fileManager, null, null, null, compilationUnits).call()) {
            throw new AgentScriptCompilerException("Could not compile the agent script.");
        }

        // Delete the compiled file on exit
        packagePath.resolve(javaClassName + ".class").toFile().deleteOnExit();

        // Load it
        try {
            addURL(tempDirectoryPath.toFile().toURI().toURL());
        } catch (MalformedURLException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        try {
            return ClassLoader.getSystemClassLoader().loadClass(JAVA_PACKAGE_NAME + "." + javaClassName)
                    .asSubclass(IAgentScript.class);
        } catch (ClassNotFoundException | ClassCastException e) {
            // Should not happen
            throw new RuntimeException(e);
        }
    }

    @Override
    public IAgentScript compile(String agentScriptName, String agentScriptSource) throws AgentScriptCompilerException {
        Class<? extends IAgentScript> agentScriptClass =
                compileJavaSource(agentScriptName, compileAgentScriptSource(agentScriptName, agentScriptSource));

        try {
            return agentScriptClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // Should not happen
            throw new RuntimeException(e);
        }
    }
}
