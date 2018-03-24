package agent_script.compiler;

import agent_script.compiler.analyzer.Symbol;

import java.nio.file.Path;
import java.util.Map;

/**
 * The compiler interface.
 */
public interface ICompiler {
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
