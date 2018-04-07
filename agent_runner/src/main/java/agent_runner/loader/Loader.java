package agent_runner.loader;

import agent_game.simulator.DefaultSimulator;

import java.nio.file.Path;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public interface Loader {
    /**
     * The formatter used for all loggers.
     */
    Formatter FORMATTER = new SimpleFormatter() {
        @Override
        public synchronized String format(LogRecord logRecord) {
            return String.format("[%1$tF %1$tT] [%2$-7s] %3$s %n",
                    new Date(logRecord.getMillis()),
                    logRecord.getLevel().getLocalizedName(),
                    logRecord.getMessage()
            );
        }
    };

    DefaultSimulator load(Path settingsPath);
}
