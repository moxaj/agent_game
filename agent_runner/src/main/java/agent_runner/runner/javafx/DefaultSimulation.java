package agent_runner.runner.javafx;

import agent_game.game.GameState;
import agent_game.simulator.Simulator;
import javafx.animation.AnimationTimer;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;

/**
 * The default {@link Simulation} implementation.
 */
public class DefaultSimulation implements Simulation {
    /**
     * The maximum simulation speed.
     */
    private static final int MAX_SPEED = 5;

    /**
     * The default refresh rate of an {@link AnimationTimer}.
     */
    private static final int FRAMES_PER_SECOND = 60;

    /**
     * The number of simulated rounds per second at the base speed.
     */
    private static final int BASE_ROUNDS_PER_SECOND = 2;

    /**
     * The simulation timer.
     */
    private final AnimationTimer timer;

    /**
     * The state of the simulation.
     */
    private final ObjectProperty<State> state;

    /**
     * The speed of the simulation.
     */
    private final IntegerProperty speed;

    /**
     * The counter used by the timer to track the elapsed time.
     */
    private final IntegerProperty counter;

    /**
     * The simulator.
     */
    private Simulator simulator;

    public DefaultSimulation() {
        this.state = new SimpleObjectProperty<>(State.NOT_LOADED);
        this.speed = new SimpleIntegerProperty(0);
        this.counter = new SimpleIntegerProperty(0);

        int speedModulo = (int) Math.pow(2, MAX_SPEED) * FRAMES_PER_SECOND / BASE_ROUNDS_PER_SECOND;
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                counter.set((counter.get() + 1) % speedModulo);
                if (state.get() == State.RUNNING && (counter.get() % Math.pow(2, MAX_SPEED - speed.get())) == 0) {
                    step();
                }
            }
        };
        this.timer.start();
    }

    @Override
    public void initialize(Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public GameState getGameState() {
        return simulator.getGameState();
    }

    @Override
    public Observable getTick() {
        return counter;
    }

    @Override
    public State getState() {
        return state.get();
    }

    @Override
    public ObservableObjectValue<State> stateProperty() {
        return state;
    }

    @Override
    public int getSpeed() {
        return speed.get();
    }

    @Override
    public ObservableIntegerValue speedProperty() {
        return Bindings.createIntegerBinding(() -> (int) Math.pow(2, speed.get()), speed);
    }

    @Override
    public void shutdown() {
        if (simulator != null) {
            simulator.shutdown();
        }

        timer.stop();
    }

    @Override
    public void stop() {
        state.set(State.STOPPED);
    }

    @Override
    public void restart() {
        simulator.reset();
        state.set(State.PAUSED);
        speed.set(0);
        counter.set(0);
    }

    @Override
    public void pause() {
        state.set(State.PAUSED);
    }

    @Override
    public void resume() {
        state.set(State.RUNNING);
    }

    @Override
    public void step() {
        simulator.step();
    }

    @Override
    public boolean canDecreaseSpeed() {
        return speed.get() > 0;
    }

    @Override
    public boolean canIncreaseSpeed() {
        return speed.get() < MAX_SPEED;
    }

    @Override
    public void decreaseSpeed() {
        int speedValue = speed.get();
        if (speedValue > 0) {
            speed.set(speedValue - 1);
        }
    }

    @Override
    public void increaseSpeed() {
        int speedValue = speed.get();
        if (speedValue < MAX_SPEED) {
            speed.set(speedValue + 1);
        }
    }
}
