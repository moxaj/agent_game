package agent_game.game;

import java.util.List;
import java.util.Map;

/**
 * Represents a team of agents.
 */
public final class Team {
    /**
     * The name of the team.
     */
    private final String name;

    /**
     * The shared memory of the team.
     */
    private final Map<Object, Object> memory;

    /**
     * The agents within the team.
     */
    private List<Agent> agents;

    public Team(String name, Map<Object, Object> memory) {
        this.name = name;
        this.memory = memory;
    }

    /**
     * @return the value of {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value of {@link #memory}
     */
    public Map<Object, Object> getMemory() {
        return memory;
    }

    /**
     * @return the value of {@link #agents}
     */
    public List<Agent> getAgents() {
        return agents;
    }

    /**
     * @param agents the value of {@link #agents}
     */
    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    /**
     * Resets the team.
     */
    public void reset() {
        memory.clear();
    }

    /**
     * @return the total energy of the team
     */
    public int getEnergy() {
        return agents.stream().mapToInt(Agent::getEnergy).sum();
    }

    /**
     * @return whether the team is eliminated
     */
    public boolean isEliminated() {
        return agents.stream().allMatch(Agent::isEliminated);
    }
}
