package agent_game.agent;

import agent_game.script.IAgentScript;

/**
 * Represents a simulated agent.
 */
public interface IAgent {
    /**
     * @return the name of the agent
     */
    String getName();

    /**
     * @return the memory of the agent
     */
    IAgentMemory getMemory();

    /**
     * @return the reasoning script of the agent
     */
    IAgentScript getScript();

    /**
     * @return the remaining time quota for the agent
     */
    long getRemainingTimeQuota();

    /**
     * Decreases the remaining time quota for the agent.
     *
     * @param amount the amount by which to decrease
     */
    void decreaseRemainingTimeQuota(long amount);


}
