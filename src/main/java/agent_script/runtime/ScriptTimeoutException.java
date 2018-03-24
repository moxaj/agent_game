package agent_script.runtime;

/**
 * Represents an exception thrown when a script times out.
 */
@SuppressWarnings("unused")
public class ScriptTimeoutException extends java.lang.RuntimeException {
    public ScriptTimeoutException() {

    }

    public ScriptTimeoutException(String message) {
        super(message);
    }

    public ScriptTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptTimeoutException(Throwable cause) {
        super(cause);
    }
}
