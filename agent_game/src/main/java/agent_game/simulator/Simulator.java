package agent_game.simulator;

import agent_game.game.GameState;

/**
 * Represents the game simulator.
 */
public interface Simulator {
    /**
     * Terminates the simulator.
     */
    void shutdown();

    /**
     * Resets the simulator.
     */
    void reset();

    /**
     * Steps the simulator.
     */
    void step();

    /**
     * @return the current state of the game
     */
    GameState getGameState();
}
