package agent_game.script;

/**
 * Custom agent script runtime exception.
 */
public class AgentScriptRuntimeException extends RuntimeException {
    public AgentScriptRuntimeException() {
        super();
    }

    public AgentScriptRuntimeException(String message) {
        super(message);
    }

    public AgentScriptRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgentScriptRuntimeException(Throwable cause) {
        super(cause);
    }
}
