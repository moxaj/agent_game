package agent_script.compiler.analyzer.namespace;

import agent_script.compiler.analyzer.Location;
import agent_script.compiler.analyzer.Symbol;

/**
 * Represents a function definition.
 */
public final class FunctionDefinition extends ElementDefinition {
    /**
     * The arity of the function.
     */
    private int arity;

    /**
     * Whether the function has an explicit return statement.
     */
    private boolean hasExplicitReturn;

    public FunctionDefinition(Symbol name, Location location) {
        super(name, location);
    }

    /**
     * @return the value of {@link #arity}
     */
    public int getArity() {
        return arity;
    }

    /**
     * @param arity the value of {@link #arity}
     */
    public void setArity(int arity) {
        this.arity = arity;
    }

    /**
     * @return the value of {@link #hasExplicitReturn}
     */
    public boolean hasExplicitReturn() {
        return hasExplicitReturn;
    }

    /**
     * @param hasExplicitReturn the value of {@link #hasExplicitReturn}
     */
    public void setHasExplicitReturn(boolean hasExplicitReturn) {
        this.hasExplicitReturn = hasExplicitReturn;
    }

    /**
     * @return whether the function has a 'macro' meta
     */
    public boolean isMacro() {
        return getMeta().contains(Symbol.asNameSymbol("macro"));
    }
}
