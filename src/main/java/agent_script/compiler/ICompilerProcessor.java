package agent_script.compiler;

/**
 * Generic compiler processor interface.
 */
public interface ICompilerProcessor {
    /**
     * Processes a namespace bundle.
     *
     * @param namespaceBundle the namespace bundle to process
     * @throws CompilerException if any fatal error occurs during the processing
     */
    void process(NamespaceBundle namespaceBundle) throws CompilerException;
}
