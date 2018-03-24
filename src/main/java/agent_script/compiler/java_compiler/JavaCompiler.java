package agent_script.compiler.java_compiler;

import agent_script.compiler.CompilerException;
import agent_script.compiler.CompilerMessageReporter;
import agent_script.compiler.CompilerProcessor;
import agent_script.compiler.NamespaceBundle;
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
    private final Path tempDirectoryPath;

    public JavaCompiler(CompilerMessageReporter messageReporter) {
        super(messageReporter);

        try {
            tempDirectoryPath = Files.createTempDirectory("temp");
        } catch (IOException e) {
            // TODO reportMessage "Could not create a necessary temporary directory."
            throw new CompilerException();
        }

        try {
            addURL(tempDirectoryPath.toFile().toURI().toURL());
        } catch (MalformedURLException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.walk(tempDirectoryPath)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(file -> {
                            if (!file.delete()) {
                                // TODO reportMessage
                                throw new CompilerException();
                            }
                        });
            } catch (IOException e) {
                // TODO reportMessage
                throw new CompilerException();
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

        // Create the java source file
        String javaPackageName = namespaceDefinition.getName().getJavaPackageName();
        String javaClassName = namespaceDefinition.getName().getJavaClassName();
        Path javaSourcePath = tempDirectoryPath.resolve(javaPackageName.replaceAll("\\.", "/")).resolve(javaClassName + ".java");

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
            // TODO reportMessage "Could not create a necessary temporary file."
            throw new CompilerException();
        }

        // Write its contents
        try (PrintWriter printWriter = new PrintWriter(javaSourcePath.toFile())) {
            printWriter.print(namespaceBundle.getJavaSource());
        } catch (FileNotFoundException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        // Compile it
        // TODO logging
        javax.tools.JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, null);
        List<String> options = Arrays.asList(
                "-classpath",
                System.getProperty("java.class.path") + ";" + tempDirectoryPath.toAbsolutePath().toFile().toString());
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(
                Collections.singletonList(javaSourcePath.toFile()));
        if (!javaCompiler.getTask(null, fileManager, null, options, null, compilationUnits).call()) {
            // TODO reportMessage "Could not compile the game script."
            throw new CompilerException();
        }

        // Load it
        try {
            namespaceBundle.setJavaClass(ClassLoader.getSystemClassLoader().loadClass(javaPackageName + "." + javaClassName));
        } catch (ClassNotFoundException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

    }
}
