package agent_script.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A component through which all compiler messages are reported and logged.
 */
public class CompilerMessageReporter {
    /**
     * The logger instance.
     */
    private final Logger logger;

    /**
     * The list of generated messages.
     */
    private final List<CompilerMessage> messages = new ArrayList<>();

    public CompilerMessageReporter(Logger logger) {
        this.logger = logger;
    }

    /**
     * Reports a message.
     *
     * @param message the message
     */
    public void report(CompilerMessage message) {
        logger.log(message.asLogRecord());
        messages.add(message);
    }

    /**
     * @return the value of {@link #messages}
     */
    public List<CompilerMessage> getMessages() {
        return messages;
    }
}
