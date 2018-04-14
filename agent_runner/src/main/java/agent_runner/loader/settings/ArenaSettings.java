package agent_runner.loader.settings;

/**
 * Represents the input settings for the arena.
 */
public final class ArenaSettings {
    /**
     * The width of the arena.
     */
    private Integer width;

    /**
     * The height of the arena.
     */
    private Integer height;

    /**
     * @return the value of {@link #width}
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width the value of {@link #width}
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return the value of {@link #height}
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height the value of {@link #height}
     */
    public void setHeight(Integer height) {
        this.height = height;
    }
}
