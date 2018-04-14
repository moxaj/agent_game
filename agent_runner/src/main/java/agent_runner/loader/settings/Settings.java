package agent_runner.loader.settings;

/**
 * Represents the main settings read from settings.yaml.
 */
public final class Settings {
    /**
     * The logging related settings.
     */
    private LoggingSettings loggingSettings;

    /**
     * The simulator settings.
     */
    private SimulatorSettings simulatorSettings;

    /**
     * @return the value of {@link #loggingSettings}
     */
    public LoggingSettings getLoggingSettings() {
        return loggingSettings;
    }

    /**
     * @param loggingSettings the value of {@link #loggingSettings}
     */
    public void setLoggingSettings(LoggingSettings loggingSettings) {
        this.loggingSettings = loggingSettings;
    }

    /**
     * @return the value of {@link #simulatorSettings}
     */
    public SimulatorSettings getSimulatorSettings() {
        return simulatorSettings;
    }

    /**
     * @param simulatorSettings the value of {@link #simulatorSettings}
     */
    public void setSimulatorSettings(SimulatorSettings simulatorSettings) {
        this.simulatorSettings = simulatorSettings;
    }
}
