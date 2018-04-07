package agent_game.game;

import agent_script.runtime.ScriptExecutionException;
import agent_script.runtime.ScriptPanicException;
import agent_script.runtime.ScriptTimeoutException;

import java.util.Map;

/**
 * Represents the reasoning script of an agent.
 */
public interface AgentScript {
    /**
     * Executes the agent script.
     *
     * @param state      the state of the agent
     * @param memory     the memory of the agent
     * @param teamMemory the team memory of the agent
     * @param statistics the statistics collected by the agent
     * @return the action to take
     * @throws ScriptPanicException     if the agent panics
     * @throws ScriptTimeoutException   if the agent times out
     * @throws ScriptExecutionException if the agent script throws any exception
     */
    AgentAction execute(
            Map<Object, Object> state,
            Map<Object, Object> memory,
            Map<Object, Object> teamMemory,
            Map<Object, Object> statistics)
            throws ScriptPanicException, ScriptTimeoutException, ScriptExecutionException;
}
