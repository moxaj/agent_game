package agent_script.runtime;

/**
 * Represents a generic wrapper exception around a runtime exception which happens during the execution of an script.
 */
@SuppressWarnings("unused")
public class ScriptExecutionException extends RuntimeException {
    public ScriptExecutionException() {
    }

    public ScriptExecutionException(String message) {
        super(message);
    }

    public ScriptExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptExecutionException(Throwable cause) {
        super(cause);
    }
}
