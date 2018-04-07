package agent_game.game;

/**
 * Represents the game arena.
 */
public class Arena {
    /**
     * The width of the arena.
     */
    private final int width;

    /**
     * The height of the arena.
     */
    private final int height;

    /**
     * The cells of the arena.
     */
    private final ArenaCell[][] cells;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new ArenaCell[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[y][x] = new ArenaCell(x, height - y - 1);
            }
        }
    }

    /**
     * @return the value of {@link #width}
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the value of {@link #height}
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns a cell, or null if the coordinates are out of bounds.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the cell or null
     */
    public ArenaCell getCell(int x, int y) {
        return x < 0 || y < 0 || x >= width || y >= height
                ? null
                : cells[height - y - 1][x];
    }

    /**
     * Resets the arena.
     */
    public void reset() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[y][x] = new ArenaCell(x, height - y - 1);
            }
        }
    }
}
