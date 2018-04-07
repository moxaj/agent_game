package agent_game.game;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Represents an agent.
 */
public class Agent {
    // Metadata

    /**
     * The globally unique index of the agent.
     */
    private final int index;

    /**
     * The name of the agent.
     */
    private final String name;

    /**
     * The team name of the agent.
     */
    private final String teamName;

    /**
     * The personal logger of the agent.
     */
    private final Logger logger;

    /**
     * The reasoning script of the agent.
     */
    private final AgentScript script;

    /**
     * The memory of the agent.
     */
    private final Map<Object, Object> memory;

    /**
     * The team memory of the agent.
     */
    private final Map<Object, Object> teamMemory;

    /**
     * The statistics collected by agent.
     */
    private final Map<Object, Object> statistics;

    // Game state

    /**
     * The x coordinate of the agent.
     */
    private int x;

    /**
     * The y coordinate of the agent.
     */
    private int y;

    /**
     * The direction of the agent.
     */
    private int direction;

    /**
     * The remaining energy of the agent.
     */
    private int energy;

    /**
     * The state of the agent.
     */
    private State state;

    public Agent(int index, String name, String teamName, Logger logger, AgentScript script, Map<Object, Object> teamMemory) {
        this.index = index;
        this.name = name;
        this.teamName = teamName;
        this.logger = logger;
        this.script = script;
        this.memory = new HashMap<>();
        this.teamMemory = teamMemory;
        this.statistics = new HashMap<>();

        reset(-1, -1, 0, -1);
    }

    /**
     * @return the value of {@link #index}
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the value of {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value of {@link #teamName}
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @return the value of {@link #logger}
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @return the value of {@link #script}
     */
    public AgentScript getScript() {
        return script;
    }

    /**
     * @return the value of {@link #memory}
     */
    public Map<Object, Object> getMemory() {
        return memory;
    }

    /**
     * @return the value of {@link #teamMemory}
     */
    public Map<Object, Object> getTeamMemory() {
        return teamMemory;
    }

    /**
     * @return the value of {@link #statistics}
     */
    public Map<Object, Object> getStatistics() {
        return statistics;
    }

    /**
     * @return the value of {@link #x}
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the value of {@link #x}
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the value of {@link #y}
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the value of {@link #y}
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the value of {@link #direction}
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the value of {@link #direction}
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the value of {@link #energy}
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * @param energy the value of {@link #energy}
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * @return the value of {@link #state}
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the value of {@link #state}
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return whether the agent has been eliminated
     */
    public boolean isEliminated() {
        return state != State.ALIVE;
    }

    /**
     * Resets the agent.
     */
    public void reset(int x, int y, int direction, int energy) {
        this.memory.clear();
        this.teamMemory.clear();
        this.statistics.clear();
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.energy = energy;
        this.state = State.ALIVE;
    }

    /**
     * Represents the state of the agent.
     */
    public enum State {
        /**
         * The agent is alive.
         */
        ALIVE,

        /**
         * The agent ran out of energy.
         */
        NO_ENERGY,

        /**
         * The agent's script has panicked.
         */
        PANICKED,

        /**
         * The agent's script has timed out.
         */
        TIMED_OUT,

        /**
         * The agent's script has crashed.
         */
        CRASHED
    }
}
