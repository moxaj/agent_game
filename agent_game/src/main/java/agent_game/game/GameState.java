package agent_game.game;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the game state.
 */
public final class GameState {
    /**
     * The participating teams.
     */
    private final List<Team> teams;

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

    public GameState(List<Team> teams, Arena arena, GameParameters parameters) {
        this.teams = teams;
        this.arena = arena;
        this.parameters = parameters;
        this.round = 0;
        this.finished = false;
    }

    /**
     * @return the value of {@link #teams}
     */
    public List<Team> getTeams() {
        return teams;
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

    /**
     * @return the list of all participating agents
     */
    public List<Agent> getAgents() {
        return teams.stream().flatMap(team -> team.getAgents().stream()).collect(Collectors.toList());
    }
}
