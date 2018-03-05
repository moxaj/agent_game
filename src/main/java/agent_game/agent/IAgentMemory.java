package agent_game.agent;

/**
 * Represents the persistent memory of an agent.
 */
public interface IAgentMemory {
    /**
     * Returns the value associated with a name.
     *
     * @param name the name
     * @return the value
     */
    Object getValue(String name);

    /**
     * Associates a value with a name.
     *
     * @param name  the name
     * @param value the value
     */
    void setValue(String name, Object value);
}
