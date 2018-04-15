package agent_runner.runner.javafx;

import agent_game.game.GameState;
import agent_game.simulator.Simulator;
import javafx.beans.Observable;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;

/**
 * An abstraction above {@link Simulator}, expected to be driven from the user interface.
 */
public interface Simulation {
    /**
     * Initializes the simulation.
     *
     * @param simulator the simulator to use
     */
    void initialize(Simulator simulator);

    /**
     * @return the current state of the game
     */
    GameState getGameState();

    /**
     * @return the periodically updated observable
     */
    Observable getTick();

    /**
     * @return the state of the simulation
     */
    default State getState() {
        return stateProperty().get();
    }

    /**
     * @return the observable state of the simulation
     */
    ObservableObjectValue<State> stateProperty();

    /**
     * @return the speed of the simulation
     */
    default int getSpeed() {
        return speedProperty().get();
    }

    /**
     * @return the observable speed of the simulation
     */
    ObservableIntegerValue speedProperty();

    /**
     * Shuts the simulation down.
     */
    void shutdown();

    /**
     * Stops the simulation.
     */
    void stop();

    /**
     * Restarts the simulation.
     */
    void restart();

    /**
     * Pauses the simulation.
     */
    void pause();

    /**
     * Resumes the simulation.
     */
    void resume();

    /**
     * Steps the simulation.
     */
    void step();

    /**
     * @return whether the simulation speed can be decreased
     */
    boolean canDecreaseSpeed();

    /**
     * @return whether the simulation speed can be increased
     */
    boolean canIncreaseSpeed();

    /**
     * Decreases the simulation speed, if possible.
     */
    void decreaseSpeed();

    /**
     * Increases the simulation speed, if possible.
     */
    void increaseSpeed();

    /**
     * The state of the simulation.
     */
    enum State {
        /**
         * No simulation loaded yet.
         */
        NOT_LOADED,

        /**
         * The simulation is stopped.
         */
        STOPPED,

        /**
         * The simulation is paused.
         */
        PAUSED,

        /**
         * The simulation is running.
         */
        RUNNING
    }
}
