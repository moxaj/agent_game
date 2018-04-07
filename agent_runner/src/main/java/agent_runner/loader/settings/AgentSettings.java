package agent_runner.loader.settings;

/**
 * Represents the settings of an agent.
 */
public class AgentSettings {
    /**
     * The name of the agent.
     */
    private String name;

    /**
     * The root source paths for the agent script.
     */
    private String rootSourcePath;

    /**
     * The main function name of the agent script.
     */
    private String mainFunctionName;

    /**
     * @return the value of {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the value of {@link #name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value of {@link #rootSourcePath}
     */
    public String getRootSourcePath() {
        return rootSourcePath;
    }

    /**
     * @param rootSourcePath the value of {@link #rootSourcePath}
     */
    public void setRootSourcePath(String rootSourcePath) {
        this.rootSourcePath = rootSourcePath;
    }

    /**
     * @return the value of {@link #mainFunctionName}
     */
    public String getMainFunctionName() {
        return mainFunctionName;
    }

    /**
     * @param mainFunctionName the value of {@link #mainFunctionName}
     */
    public void setMainFunctionName(String mainFunctionName) {
        this.mainFunctionName = mainFunctionName;
    }
}
