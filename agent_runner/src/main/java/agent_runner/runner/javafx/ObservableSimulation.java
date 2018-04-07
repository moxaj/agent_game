package agent_runner.runner.javafx;

import agent_game.game.GameState;
import agent_game.simulator.Simulator;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * An abstraction layer above {@link Simulator}, expected to be driven from the user interface.
 */
public class ObservableSimulation {
    /**
     * The maximum simulation speed.
     */
    private static final int MAX_SPEED = 5;

    /**
     * The frame time at the lowest speed, in milliseconds.
     */
    private static final int BASE_FRAME_TIME_MS = 500;

    /**
     * The simulation scheduler service.
     */
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * The runnable to call when the simulator state has changed.
     */
    private final Runnable refreshRunnable;

    /**
     * The state of the simulation.
     */
    private final ObjectProperty<State> state;

    /**
     * The current round.
     */
    private final IntegerProperty round;

    /**
     * The speed of the simulation.
     */
    private final IntegerProperty speed;

    /**
     * The simulation future.
     */
    private final ScheduledFuture<?> future;

    /**
     * The counter used by the future to track the elapsed time.
     */
    private int counter;

    /**
     * The simulator.
     */
    private Simulator simulator;

    /**
     * The observable game state.
     */
    private ObservableGameState observableGameState;

    // Public API

    public ObservableSimulation(Runnable refreshRunnable) {
        this.refreshRunnable = refreshRunnable;
        this.state = new SimpleObjectProperty<>(State.NOT_LOADED);
        this.round = new SimpleIntegerProperty(-1);
        this.speed = new SimpleIntegerProperty(0);

        int speedModulo = (int) Math.pow(2, MAX_SPEED);
        this.future = executorService.scheduleAtFixedRate(() -> {
            counter = (counter + 1) % speedModulo;
            if (state.get() == ObservableSimulation.State.RUNNING && (counter % Math.pow(2, MAX_SPEED - speed.get())) == 0) {
                Platform.runLater(this::step);
            }
        }, 0, (long) (BASE_FRAME_TIME_MS / speedModulo), TimeUnit.MILLISECONDS);
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
        this.observableGameState = new ObservableGameState(simulator.getGameState(), roundProperty());
    }

    public GameState getGameState() {
        return simulator.getGameState();
    }

    public ObservableGameState getObservableGameState() {
        return observableGameState;
    }

    public State getState() {
        return state.get();
    }

    public ObjectProperty<State> stateProperty() {
        return state;
    }

    public int getRound() {
        return round.get();
    }

    public IntegerProperty roundProperty() {
        return round;
    }

    public int getSpeed() {
        return speed.get();
    }

    public IntegerProperty speedProperty() {
        return speed;
    }

    public boolean canDecreaseSpeed() {
        return speed.get() > 0;
    }

    public boolean canIncreaseSpeed() {
        return speed.get() < MAX_SPEED;
    }

    public void stop() {
        state.set(State.STOPPED);
    }

    public void restart() {
        simulator.restart();
        state.set(State.PAUSED);
        speed.set(0);
        round.set(0);
        counter = 0;
        refreshRunnable.run();
    }

    public void pause() {
        state.set(State.PAUSED);
    }

    public void resume() {
        state.set(State.RUNNING);
    }

    public void step() {
        simulator.step();
        round.set(round.get() + 1);
        refreshRunnable.run();
    }

    public void decreaseSpeed() {
        if (canDecreaseSpeed()) {
            speed.set(speed.get() - 1);
        }
    }

    public void increaseSpeed() {
        if (canIncreaseSpeed()) {
            speed.set(speed.get() + 1);
        }
    }

    public void shutdown() {
        if (simulator != null) {
            simulator.stop();
        }

        future.cancel(false);
        executorService.shutdownNow();
    }

    public enum State {
        /**
         * No simulation loaded yet.
         */
        NOT_LOADED,

        /**
         * The current simulation is stopped.
         */
        STOPPED,

        /**
         * The current simulation is paused.
         */
        PAUSED,

        /**
         * The current simulation is running.
         */
        RUNNING
    }
}
