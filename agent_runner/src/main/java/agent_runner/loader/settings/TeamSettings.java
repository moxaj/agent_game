package agent_runner.loader.settings;

/**
 * Represents the team related settings.
 */
public class TeamSettings {
    /**
     * The name of the team.
     */
    private String name;

    /**
     * The settings of the agents in this team.
     */
    private AgentSettings[] agents;

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
     * @return the value of {@link #agents}
     */
    public AgentSettings[] getAgents() {
        return agents;
    }

    /**
     * @param agents the value of {@link #agents}
     */
    public void setAgents(AgentSettings[] agents) {
        this.agents = agents;
    }
}
