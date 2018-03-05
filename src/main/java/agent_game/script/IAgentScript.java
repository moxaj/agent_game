package agent_game.script;

import agent_game.agent.IAgentAction;
import agent_game.agent.IAgentMemory;
import agent_game.agent.IAgentPerception;
import agent_game.simulator.IAgentGameSimulatorEnvironment;

/**
 * Represents the reasoning script of a simulated agent.
 */
public interface IAgentScript {
    /**
     * Executes the reasoning script of the agent.
     *
     * @param environment the game simulator environment
     * @param memory      the memory of the agent
     * @param perception  the perception of the agent
     * @return the action to be taken by the agent
     * @throws InterruptedException        if the agent times out
     * @throws AgentScriptRuntimeException if any other error occurs
     */
    IAgentAction execute(
            IAgentGameSimulatorEnvironment environment,
            IAgentMemory memory,
            IAgentPerception perception)
            throws InterruptedException, AgentScriptRuntimeException;
}
