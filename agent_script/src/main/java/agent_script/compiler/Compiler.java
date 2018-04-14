package agent_script.compiler;

import agent_script.compiler.analyzer.Symbol;

import java.nio.file.Path;
import java.util.Map;

/**
 * The compiler interface.
 */
public interface Compiler {
    /**
     * The magic prefix with which to munge all emitted symbols.
     */
    String MAGIC_PREFIX = "_";

    /**
     * Munges the given name to avoid conflict with any reserved java keywords.
     *
     * @param name the name to munge
     * @return the munged name
     */
    static String munge(String name) {
        return MAGIC_PREFIX + name;
    }

    /**
     * Compiles all source files which are found in the given root source paths.
     *
     * @param rootSourcePaths the root source paths
     * @return a map from namespace names to compiled classes
     * @throws CompilerException if a compilation error occurs
     */
    Map<Symbol, Class<?>> compile(Path[] rootSourcePaths) throws CompilerException;

    /**
     * @return the collected compiler diagnostics
     */
    CompilerDiagnostics getDiagnostics();
}
