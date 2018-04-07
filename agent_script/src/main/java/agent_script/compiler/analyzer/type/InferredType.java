package agent_script.compiler.analyzer.type;

/**
 * The inferred type of an expression.
 */
public enum InferredType {
    /**
     * Number.
     */
    NUMBER,

    /**
     * Boolean.
     */
    BOOLEAN,

    /**
     * String
     */
    STRING,

    /**
     * Unknown
     */
    UNKNOWN,

    /**
     * Nil
     */
    NIL;

    /**
     * @return whether the inferred type may be a number
     */
    public boolean isMaybeNumber() {
        return this == UNKNOWN || this == NUMBER;
    }

    /**
     * @return whether the inferred type may be a boolean
     */
    public boolean isMaybeBoolean() {
        return this == UNKNOWN || this == BOOLEAN;
    }

    @Override
    public String toString() {
        switch (this) {
            case NUMBER:
                return "number";
            case BOOLEAN:
                return "boolean";
            case STRING:
                return "string";
            case UNKNOWN:
                return "?";
            case NIL:
                return "nil";
            default:
                throw new RuntimeException();
        }
    }
}