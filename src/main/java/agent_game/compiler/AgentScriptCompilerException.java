package agent_game.compiler;

/**
 * Represents an agent script compiler exception.
 */
public class AgentScriptCompilerException extends Exception {
    public AgentScriptCompilerException() {
        super();
    }

    public AgentScriptCompilerException(String message) {
        super(message);
    }

    public AgentScriptCompilerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgentScriptCompilerException(Throwable cause) {
        super(cause);
    }
}
