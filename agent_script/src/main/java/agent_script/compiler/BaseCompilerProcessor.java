package agent_script.compiler;

import agent_script.compiler.analyzer.Location;
import agent_script.compiler.analyzer.Symbol;
import agent_script.compiler.analyzer.namespace.ConstantDefinition;
import agent_script.compiler.analyzer.namespace.FunctionDefinition;
import agent_script.compiler.analyzer.namespace.NamespaceDefinition;
import org.antlr.v4.runtime.Token;

import java.util.Map;

/**
 * Abstract implementation of {@link CompilerProcessor}.
 */
public abstract class BaseCompilerProcessor implements CompilerProcessor {
    /**
     * The message reporter.
     */
    private final CompilerMessageReporter messageReporter;

    /**
     * The namespace bundle being processed.
     */
    protected NamespaceBundle namespaceBundle;

    /**
     * The namespace definitions for all the namespaces.
     */
    protected Map<Symbol, NamespaceDefinition> namespaceDefinitions = null;

    public BaseCompilerProcessor(CompilerMessageReporter messageReporter) {
        this.messageReporter = messageReporter;
    }

    /**
     * Report a compiler message.
     *
     * @param message the compiler message
     */
    protected void reportMessage(CompilerMessage message) {
        messageReporter.report(message);
    }

    /**
     * Qualifies the symbol with the current namespace.
     *
     * @param symbol the symbol to qualify
     * @return the qualified symbol
     */
    protected Symbol qualifySymbol(Symbol symbol) {
        return symbol.qualify(namespaceBundle.getNamespaceDefinition().getName().getNamespaceSymbol());
    }

    /**
     * Resolves a name into a namespace definition.
     *
     * @param namespaceName the name of the namespace
     * @return the resolved namespace
     */
    protected NamespaceDefinition resolvedNamespaceDefinition(Symbol namespaceName) {
        assert namespaceDefinitions != null;
        NamespaceDefinition namespaceDefinition = namespaceBundle.getNamespaceDefinition();
        return namespaceDefinition.getName().equals(namespaceName)
                ? namespaceDefinition
                : namespaceDefinitions.get(namespaceDefinition.getImportedNamespaceNames().get(namespaceName));
    }

    /**
     * Resolves a name into a constant definition.
     *
     * @param constantName the name of the constant
     * @return the resolved constant
     */
    protected ConstantDefinition resolvedConstantDefinition(Symbol constantName) {
        if (constantName.isNameSymbol()) {
            constantName = qualifySymbol(constantName);
        }

        return resolvedNamespaceDefinition(constantName.getNamespaceSymbol()).getConstantDefinitions().get(constantName.getNameSymbol());
    }

    /**
     * Resolves a name into a function definition.
     *
     * @param functionName the name of the function
     * @return the resolved function
     */
    protected FunctionDefinition resolvedFunctionDefinition(Symbol functionName) {
        if (functionName.isNameSymbol()) {
            functionName = qualifySymbol(functionName);
        }

        return resolvedNamespaceDefinition(functionName.getNamespaceSymbol()).getFunctionDefinitions().get(functionName.getNameSymbol());
    }

    /**
     * Creates a location which points to a token.
     *
     * @param token the token
     * @return the new location
     */
    protected Location getLocation(Token token) {
        return new Location(namespaceBundle.getSourcePath(), token.getLine(), token.getCharPositionInLine());
    }
}
