package agent_runner.loader.settings;

/**
 * Represents the input settings for the arena.
 */
public class ArenaSettings {
    /**
     * The width of the arena.
     */
    private int width = -1;

    /**
     * The height of the arena.
     */
    private int height = -1;

    /**
     * @return the value of {@link #width}
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the value of {@link #width}
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the value of {@link #height}
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the value of {@link #height}
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
