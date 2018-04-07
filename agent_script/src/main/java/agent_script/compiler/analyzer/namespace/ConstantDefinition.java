package agent_script.compiler.analyzer.namespace;

import agent_script.compiler.analyzer.Location;
import agent_script.compiler.analyzer.Symbol;

/**
 * Represents a constant definition.
 */
public final class ConstantDefinition extends ElementDefinition {
    public ConstantDefinition(Symbol name, Location location) {
        super(name, location);
    }
}
