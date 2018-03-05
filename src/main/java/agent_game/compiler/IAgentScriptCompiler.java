package agent_game.compiler;

import agent_game.script.IAgentScript;

/**
 * Represents the agent script compiler.
 */
public interface IAgentScriptCompiler {
    /**
     * Compiles the agent script.
     *
     * @param agentScriptName   the name of the agent script
     * @param agentScriptSource the source of the agent script
     * @return an instance of the compiled script
     * @throws AgentScriptCompilerException if the agent script source is invalid
     */
    IAgentScript compile(String agentScriptName, String agentScriptSource) throws AgentScriptCompilerException;
}