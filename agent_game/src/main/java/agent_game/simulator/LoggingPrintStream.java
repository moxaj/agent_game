package agent_game.simulator;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom {@link PrintStream} implementation which logs to a logger when {@link #println} is called.
 */
public class LoggingPrintStream extends PrintStream {
    /**
     * The global null output stream.
     */
    private static final OutputStream NULL_OUTPUT_STREAM = new NullOutputStream();

    /**
     * The logger instance.
     */
    private final Logger logger;

    public LoggingPrintStream(Logger logger) {
        super(NULL_OUTPUT_STREAM);
        this.logger = logger;
    }

    @Override
    public void println(Object x) {
        logger.log(Level.INFO, x.toString());
    }

    /**
     * An {@link OutputStream} implementation which simply swallows the {@link #write(int)} calls.
     */
    private static class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) {
            // Do nothing
        }
    }
}
