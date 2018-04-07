package agent_game.simulator;

import agent_game.game.GameState;

/**
 * Represents the game simulator.
 */
public interface Simulator {
    /**
     * Stops the simulator.
     */
    void stop();

    /**
     * Restarts the simulator.
     */
    void restart();

    /**
     * Steps the simulator.
     */
    void step();

    /**
     * @return the current state of the game
     */
    GameState getGameState();
}
