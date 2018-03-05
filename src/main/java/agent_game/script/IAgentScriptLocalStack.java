package agent_game.script;

/**
 * Represents an emulated local variable stack.
 */
public interface IAgentScriptLocalStack {
    /**
     * Gets the value of a variable.
     *
     * @param name the name of the variable
     * @return the value of the variable
     */
    Object getLocal(String name);

    /**
     * Sets the value of a variable.
     *
     * @param name  the name of the variable
     * @param value the value of the variable
     */
    void setLocal(String name, Object value);

    /**
     * Allocates a new stack which inherits the locals of the current stack.
     *
     * @return the new stack
     */
    IAgentScriptLocalStack push();

    /**
     * Destroys the current stack.
     *
     * @return the parent stack
     */
    IAgentScriptLocalStack pop();
}
