package agent_runner.loader;

/**
 * Represents an exception thrown by the JavaFX application.
 */
public class LoaderException extends RuntimeException {
    public LoaderException() {
    }

    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
