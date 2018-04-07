package agent_runner.visualizer;

import agent_game.game.GameState;
import javafx.scene.canvas.Canvas;

/**
 * Represents the main visualizer.
 */
public interface Visualizer {
    /**
     * Initializes the visualizer.
     *
     * @param gameState the game state to use
     * @param canvas    the canvas to paint to
     */
    void setup(GameState gameState, Canvas canvas);

    /**
     * Repaints the currents state on the canvas.
     */
    void repaint();
}
