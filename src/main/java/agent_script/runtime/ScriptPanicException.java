package agent_script.runtime;

/**
 * Represents a runtime exception thrown directly by the user.
 */
@SuppressWarnings("unused")
public class ScriptPanicException extends RuntimeException {
    public ScriptPanicException() {

    }

    public ScriptPanicException(String message) {
        super(message);
    }

    public ScriptPanicException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptPanicException(Throwable cause) {
        super(cause);
    }
}
