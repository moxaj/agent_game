package agent_runner.loader.settings;

/**
 * Represents the logging related settings.
 */
public class LoggingSettings {
    /**
     * The output directory where all log files are written.
     */
    private String outputPath;

    /**
     * @return the value of {@link #outputPath}
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * @param outputPath the value of {@link #outputPath}
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
