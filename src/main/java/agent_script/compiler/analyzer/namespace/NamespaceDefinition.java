package agent_script.compiler.analyzer.namespace;

import agent_script.compiler.analyzer.Location;
import agent_script.compiler.analyzer.Symbol;

import java.util.HashMap;
import java.util.Map;

public final class NamespaceDefinition extends ElementDefinition {
    /**
     * The map from imported namespace names to namespace definitions.
     */
    private final Map<Symbol, Symbol> importedNamespaceNames = new HashMap<>();

    /**
     * The map from defined constant names to constant definitions.
     */
    private final Map<Symbol, ConstantDefinition> constantDefinitions = new HashMap<>();

    /**
     * The map from defined function names to function definitions.
     */
    private final Map<Symbol, FunctionDefinition> functionDefinitions = new HashMap<>();

    public NamespaceDefinition(Symbol name, Location location) {
        super(name, location);
    }

    /**
     * @return the value of {@link #importedNamespaceNames}
     */
    public Map<Symbol, Symbol> getImportedNamespaceNames() {
        return importedNamespaceNames;
    }

    /**
     * @return the value of {@link #constantDefinitions}
     */
    public Map<Symbol, ConstantDefinition> getConstantDefinitions() {
        return constantDefinitions;
    }

    /**
     * @return the value of {@link #functionDefinitions}
     */
    public Map<Symbol, FunctionDefinition> getFunctionDefinitions() {
        return functionDefinitions;
    }
}
