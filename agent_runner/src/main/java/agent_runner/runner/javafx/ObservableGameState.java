package agent_runner.runner.javafx;

import agent_game.game.Arena;
import agent_game.game.GameParameters;
import agent_game.game.GameState;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Set;
import java.util.stream.Collectors;

public class ObservableGameState {
    /**
     * The participating agents.
     */
    private final Set<ObservableAgent> agents;

    /**
     * The game arena.
     */
    private final Arena arena;

    /**
     * The game parameters.
     */
    private final GameParameters parameters;

    /**
     * The current round.
     */
    private final IntegerProperty round;

    /**
     * Whether the game has finished.
     */
    private final BooleanProperty finished;

    public ObservableGameState(GameState gameState, Observable... observables) {
        this.agents = gameState.getAgents().stream().map(agent -> new ObservableAgent(agent, observables)).collect(Collectors.toSet());
        this.arena = gameState.getArena();
        this.parameters = gameState.getParameters();

        this.round = new SimpleIntegerProperty();
        this.round.bind(Bindings.createIntegerBinding(gameState::getRound, observables));
        this.round.addListener((observable, oldValue, newValue) -> {
            // Only here to force certain bindings to update
        });

        this.finished = new SimpleBooleanProperty();
        this.finished.bind(Bindings.createBooleanBinding(gameState::isFinished, observables));
    }

    /**
     * @return the value of {@link #agents}
     */
    public Set<ObservableAgent> getAgents() {
        return agents;
    }

    /**
     * @return the value of {@link #arena}
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * @return the value of {@link #parameters}
     */
    public GameParameters getParameters() {
        return parameters;
    }

    public int getRound() {
        return round.get();
    }

    public IntegerProperty roundProperty() {
        return round;
    }

    public boolean isFinished() {
        return finished.get();
    }

    public BooleanProperty finishedProperty() {
        return finished;
    }
}
