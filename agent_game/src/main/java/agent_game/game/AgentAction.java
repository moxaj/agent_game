package agent_game.game;

/**
 * Represents an agent action.
 */
public enum AgentAction {
    /**
     * Stay at the current position.
     */
    STAY,

    /**
     * Move forward, if possible.
     */
    MOVE,

    /**
     * Turn left.
     */
    TURN_LEFT,

    /**
     * Turn right.
     */
    TURN_RIGHT
}
