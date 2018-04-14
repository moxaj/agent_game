package agent_runner.loader.settings;

/**
 * Represents the settings related to the collected statistics.
 */
public final class StatisticsSettings {
    /**
     * The output directory where the statistics file will be written.
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
