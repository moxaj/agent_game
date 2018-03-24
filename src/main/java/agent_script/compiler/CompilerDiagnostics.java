package agent_script.compiler;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents all statistics collected by the compiler.
 */
public class CompilerDiagnostics {
    /**
     * The generated compiler messages.
     */
    private final List<CompilerMessage> messages = new ArrayList<>();

    /**
     * The time elapsed from start to finish, all processors included.
     */
    private long elapsedTime;

    /**
     * The number of error level messages generated.
     */
    private int errorCount;

    /**
     * @return the value of {@link #errorCount}
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount the value of {@link #errorCount}
     */
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * @return the value of {@link #elapsedTime}
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * @param elapsedTime the value of {@link #elapsedTime}
     */
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * @return the value of {@link #messages}
     */
    public List<CompilerMessage> getMessages() {
        return messages;
    }
}
