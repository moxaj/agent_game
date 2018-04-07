package agent_script.compiler;

import agent_script.compiler.analyzer.Symbol;
import agent_script.compiler.analyzer.namespace.NamespaceDefinition;

import java.util.Map;

/**
 * Generic compiler processor interface.
 */
public interface CompilerProcessor {
    /**
     * Processes a namespace bundle.
     *
     * @param namespaceBundle      the namespace bundle to process
     * @param namespaceDefinitions the namespace definitions
     * @throws CompilerException if any fatal error occurs during the processing
     */
    void process(NamespaceBundle namespaceBundle, Map<Symbol, NamespaceDefinition> namespaceDefinitions) throws CompilerException;
}
