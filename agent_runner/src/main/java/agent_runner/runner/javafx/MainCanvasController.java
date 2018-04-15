package agent_runner.runner.javafx;

import agent_game.game.Agent;
import agent_game.game.Arena;
import agent_game.game.GameState;
import javafx.beans.Observable;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;

/**
 * Represents the component which controls the main mainCanvas.
 */
public final class MainCanvasController {
    /**
     * The color used to paint the background.
     */
    private static final Color COLOR_BACKGROUND = Color.rgb(255, 255, 255);

    /**
     * The color used to paint the energy drinks.
     */
    private static final Color COLOR_ENERGY_DRINK = Color.rgb(0, 200, 0);

    /**
     * The color used to paint the agents.
     */
    private static final Color COLOR_AGENT = Color.rgb(30, 100, 200);

    /**
     * The color used to paint the vision ranges.
     */
    private static final Color COLOR_VISION_RANGE = Color.rgb(255, 255, 0, 0.2);

    /**
     * The color used to paint the grid.
     */
    private static final Color COLOR_GRID = Color.rgb(240, 240, 240);

    /**
     * The canvas to paint to.
     */
    private final Canvas mainCanvas;

    /**
     * The state of the game.
     */
    private GameState gameState;

    public MainCanvasController(Canvas mainCanvas) {
        this.mainCanvas = mainCanvas;
    }

    /**
     * @return the {@link javafx.scene.transform.Transform} which maps the game coordinates into canvas coordinates
     */
    private Affine makeSpaceTransform() {
        Arena arena = gameState.getArena();

        Affine spaceTransform = new Affine();
        spaceTransform.appendScale(1.0, -1.0, 0, mainCanvas.getHeight() / 2);
        spaceTransform.appendScale(mainCanvas.getWidth() / arena.getWidth(), mainCanvas.getHeight() / arena.getHeight());
        return spaceTransform;
    }

    /**
     * Initializes the controller.
     *
     * @param gameState the game state
     * @param tick      the observable to listen to
     */
    public void initialize(GameState gameState, Observable tick) {
        this.gameState = gameState;

        GraphicsContext g = mainCanvas.getGraphicsContext2D();
        g.setTransform(makeSpaceTransform());
        g.setFont(Font.font("Consolas", FontWeight.EXTRA_BOLD, 18));
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);

        tick.addListener(observable -> this.repaint());
    }

    /**
     * Paints the background.
     *
     * @param g the graphics context
     */
    private void paintBackground(GraphicsContext g) {
        Arena arena = gameState.getArena();
        g.setFill(COLOR_BACKGROUND);
        g.fillRect(0.0, 0.0, arena.getWidth(), arena.getHeight());
    }

    /**
     * Paints the agents.
     *
     * @param g the graphics context
     */
    private void paintAgents(GraphicsContext g) {
        g.setFill(COLOR_VISION_RANGE);
        int visionRange = gameState.getParameters().getVisionRange();
        gameState.getTeams().forEach(team -> {
            team.getAgents().stream().filter(agent -> !agent.isEliminated()).forEach(agent -> {
                int direction = agent.getDirection();
                double x0 = agent.getX() + 0.5;
                double y0 = agent.getY() + 0.5;
                double x1 = x0 + (direction == 1 ? 1 : -1) * visionRange;
                double x2 = x0 + (direction == 3 ? -1 : 1) * visionRange;
                double y1 = y0 + (direction == 0 ? 1 : -1) * visionRange;
                double y2 = y0 + (direction == 2 ? -1 : 1) * visionRange;
                g.fillPolygon(new double[]{x0, x1, x2}, new double[]{y0, y1, y2}, 3);
            });
        });

        Arena arena = gameState.getArena();
        for (int x = 0; x < arena.getWidth(); x++) {
            for (int y = 0; y < arena.getHeight(); y++) {
                Agent agent = arena.getCell(x, y).getAgent();
                if (agent != null) {
                    g.setFill(COLOR_AGENT);
                    g.fillOval(x + 0.1, y + 0.1, 0.8, 0.8);

                    g.save();

                    Affine textTransform = new Affine();
                    textTransform.appendTranslation(0.5, 0.45);
                    textTransform.appendScale(arena.getWidth() / mainCanvas.getWidth(), -arena.getHeight() / mainCanvas.getHeight(), x, y);
                    g.transform(textTransform);

                    g.setFill(Color.WHITE);
                    g.fillText(agent.getIndex() + "", x, y);

                    g.restore();
                }
            }
        }
    }

    /**
     * Paints the energy drinks.
     *
     * @param g the graphics context
     */
    private void paintEnergyDrinks(GraphicsContext g) {
        Arena arena = gameState.getArena();
        g.setFill(COLOR_ENERGY_DRINK);
        for (int x = 0; x < arena.getWidth(); x++) {
            for (int y = 0; y < arena.getHeight(); y++) {
                if (arena.getCell(x, y).hasEnergy()) {
                    g.fillOval(x + 0.2, y + 0.2, 0.6, 0.6);
                }
            }
        }
    }

    /**
     * Paints the grid.
     *
     * @param g the graphics context
     */
    private void paintGrid(GraphicsContext g) {
        Arena arena = gameState.getArena();
        int arenaWidth = arena.getWidth();
        int arenaHeight = arena.getHeight();

        g.setStroke(COLOR_GRID);
        g.setLineWidth(0.02);
        g.beginPath();

        for (int i = 1; i < arenaWidth; i++) {
            g.moveTo(i, 0);
            g.lineTo(i, arenaHeight);
        }

        for (int i = 1; i < arenaHeight; i++) {
            g.moveTo(0, i);
            g.lineTo(arenaWidth, i);
        }

        g.closePath();
        g.stroke();
    }

    /**
     * Repaints the canvas.
     */
    private void repaint() {
        GraphicsContext g = mainCanvas.getGraphicsContext2D();
        paintBackground(g);
        paintGrid(g);
        paintAgents(g);
        paintEnergyDrinks(g);
    }
}
