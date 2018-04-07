package agent_runner.runner.javafx;

import agent_game.game.Agent;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Map;

public class ObservableAgent {
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
     * The name of the agent's team.
     */
    private final String teamName;

    /**
     * The personal memory of the agent.
     */
    private final Map<Object, Object> memory;

    /**
     * The shared memory of the agent's team.
     */
    private final Map<Object, Object> teamMemory;

    /**
     * The statistics gathered by the agent.
     */
    private final Map<Object, Object> statistics;

    // Game state

    /**
     * The x coordinate of the agent.
     */
    private final IntegerProperty x;

    /**
     * The y coordinate of the agent.
     */
    private final IntegerProperty y;

    /**
     * The remaining energy of the agent.
     */
    private final IntegerProperty energy;

    /**
     * The direction of the agent.
     */
    private final IntegerProperty direction;

    /**
     * The state of the agent.
     */
    private final ObjectProperty<Agent.State> state;

    public ObservableAgent(Agent agent, Observable... observables) {
        index = agent.getIndex();
        name = agent.getName();
        teamName = agent.getTeamName();
        memory = agent.getMemory();
        teamMemory = agent.getTeamMemory();
        statistics = agent.getStatistics();

        x = new SimpleIntegerProperty(agent.getX());
        x.bind(Bindings.createIntegerBinding(agent::getX, observables));

        y = new SimpleIntegerProperty(agent.getY());
        y.bind(Bindings.createIntegerBinding(agent::getY, observables));

        energy = new SimpleIntegerProperty(agent.getEnergy());
        energy.bind(Bindings.createIntegerBinding(agent::getEnergy, observables));

        direction = new SimpleIntegerProperty(agent.getDirection());
        direction.bind(Bindings.createIntegerBinding(agent::getDirection, observables));

        state = new SimpleObjectProperty<>(agent.getState());
        state.bind(Bindings.createObjectBinding(agent::getState, observables));
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

    public int getX() {
        return x.get();
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public int getEnergy() {
        return energy.get();
    }

    public IntegerProperty energyProperty() {
        return energy;
    }

    public int getDirection() {
        return direction.get();
    }

    public IntegerProperty directionProperty() {
        return direction;
    }

    public Agent.State getState() {
        return state.get();
    }

    public ObjectProperty<Agent.State> stateProperty() {
        return state;
    }
}
