package agent_game.game;

import java.util.Optional;
import java.util.Set;

/**
 * Represents the game state.
 */
public class GameState {
    /**
     * The participating agents.
     */
    private final Set<Agent> agents;

    /**
     * The arena.
     */
    private final Arena arena;

    /**
     * The game parameters.
     */
    private final GameParameters parameters;

    /**
     * The current round.
     */
    private int round;

    /**
     * Whether the game has finished.
     */
    private boolean finished;

    public GameState(Set<Agent> agents, Arena arena, GameParameters parameters) {
        this.agents = agents;
        this.arena = arena;
        this.parameters = parameters;
        this.round = 0;
        this.finished = false;
    }

    /**
     * @return the value of {@link #agents}
     */
    public Set<Agent> getAgents() {
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

    /**
     * @return the value of {@link #round}
     */
    public int getRound() {
        return round;
    }

    /**
     * @param round the value of {@link #round}
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * @return the value of {@link #finished}
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @param finished the value of {@link #finished}
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
