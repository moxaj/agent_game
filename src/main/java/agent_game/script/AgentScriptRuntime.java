package agent_game.script;

/**
 * The runtime environment provided for the agent scripts.
 */
public class AgentScriptRuntime {
    /**
     * Coerces the value to a double.
     *
     * @param x the value
     * @return the coerced value
     */
    public static double asNumber(Object x) {
        try {
            return (double) x;
        } catch (ClassCastException e) {
            throw new AgentScriptRuntimeException("Value cannot be converted to a double.", e);
        }
    }

    /**
     * Coerces the value to a boolean.
     *
     * @param x the value
     * @return the coerced value
     */
    public static boolean asBoolean(Object x) {
        try {
            return (boolean) x;
        } catch (ClassCastException e) {
            throw new AgentScriptRuntimeException("Value cannot be converted to a boolean.", e);
        }
    }
}
