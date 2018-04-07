package agent_script.compiler.java_compiler;

import agent_script.compiler.*;
import agent_script.compiler.analyzer.Symbol;
import agent_script.compiler.analyzer.namespace.NamespaceDefinition;
import org.apache.commons.jci.compilers.EclipseJavaCompiler;
import org.apache.commons.jci.compilers.EclipseJavaCompilerSettings;
import org.apache.commons.jci.readers.MemoryResourceReader;
import org.apache.commons.jci.stores.MemoryResourceStore;

import java.util.HashMap;
import java.util.Map;

public class JavaCompiler extends BaseCompilerProcessor {
    private final MemoryClassLoader memoryClassLoader = new MemoryClassLoader(DefaultCompiler.class.getClassLoader());
    private final MemoryResourceReader memoryResourceReader = new MemoryResourceReader();
    private final MemoryResourceStore memoryResourceStore = new MemoryResourceStore();
    private final org.apache.commons.jci.compilers.JavaCompiler javaCompiler;

    public JavaCompiler(CompilerMessageReporter messageReporter) {
        super(messageReporter);
        EclipseJavaCompilerSettings javaCompilerSettings = new EclipseJavaCompilerSettings();
        javaCompilerSettings.setDebug(false);
        javaCompilerSettings.setSourceVersion("1.6");
        javaCompilerSettings.setTargetVersion("1.6");
        javaCompiler = new EclipseJavaCompiler(javaCompilerSettings);
    }

    @Override
    public void process(NamespaceBundle namespaceBundle, Map<Symbol, NamespaceDefinition> namespaceDefinitions) throws CompilerException {
        this.namespaceBundle = namespaceBundle;
        this.namespaceDefinitions = namespaceDefinitions;

        Symbol namespaceName = namespaceBundle.getNamespaceDefinition().getName();
        String javaClassName = namespaceName.getQualifiedJavaClassName();
        String javaPath = namespaceName.getJavaPath();
        String javaSourcePath = javaPath + ".java";
        String javaClassPath = javaPath + ".class";

        memoryResourceReader.add(javaSourcePath, namespaceBundle.getJavaSource().getBytes());
        javaCompiler.compile(new String[]{javaSourcePath}, memoryResourceReader, memoryResourceStore, memoryClassLoader);

        byte[] javaClassBytes = memoryResourceStore.read(javaClassPath);
        namespaceBundle.setJavaClassBytes(javaClassBytes);

        memoryClassLoader.addClass(javaClassName, javaClassBytes);
        Class<?> javaClass;
        try {
            javaClass = Class.forName(javaClassName, false, memoryClassLoader);
        } catch (ClassNotFoundException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        namespaceBundle.setJavaClass(javaClass);
    }

    private static class MemoryClassLoader extends ClassLoader {
        private final Map<String, Class<?>> classes = new HashMap<>();

        private MemoryClassLoader(ClassLoader parent) {
            super(parent);
        }

        private void addClass(String name, byte[] definition) {
            classes.put(name, defineClass(name, definition, 0, definition.length));
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            return classes.getOrDefault(name, super.findClass(name));
        }
    }
}
