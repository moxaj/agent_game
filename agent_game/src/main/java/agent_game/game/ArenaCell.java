package agent_game.game;

/**
 * Represents a single cell within the arena.
 */
public final class ArenaCell {
    /**
     * The x coordinate of the cell.
     */
    private final int x;

    /**
     * The y coordinate of the cell.
     */
    private final int y;

    /**
     * The current occupying agent, if any.
     */
    private Agent agent = null;

    /**
     * Whether the cell has an energy drink.
     */
    private boolean hasEnergy = false;

    public ArenaCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the value of {@link #x}
     */
    public int getX() {
        return x;
    }

    /**
     * @return the value of {@link #y}
     */
    public int getY() {
        return y;
    }

    /**
     * @return the value of {@link #agent}
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * @param agent the value of {@link #agent}
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    /**
     * @return the value of {@link #hasEnergy}
     */
    public boolean hasEnergy() {
        return hasEnergy;
    }

    /**
     * @param hasEnergy the value of {@link #hasEnergy}
     */
    public void setHasEnergy(boolean hasEnergy) {
        this.hasEnergy = hasEnergy;
    }
}
