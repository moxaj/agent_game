package agent_game.game;

/**
 * Represents the global game parameters for the simulator.
 */
public final class GameParameters {
    /**
     * The time quota for a single round of reasoning, in milliseconds.
     */
    private final int timeQuota;

    /**
     * The initial energy of the agents.
     */
    private final int initialEnergy;

    /**
     * The per round energy loss of the agents.
     */
    private final int energyLoss;

    /**
     * The amount of energy refilled by energy drinks.
     */
    private final int energyRefill;

    /**
     * The frequency of energy drink spawns.
     */
    private final int energyFrequency;

    /**
     * The vision range of the agents.
     */
    private final int visionRange;

    public GameParameters(int timeQuota, int initialEnergy, int energyLoss, int energyRefill, int energyFrequency, int visionRange) {
        this.timeQuota = timeQuota;
        this.initialEnergy = initialEnergy;
        this.energyLoss = energyLoss;
        this.energyRefill = energyRefill;
        this.energyFrequency = energyFrequency;
        this.visionRange = visionRange;
    }

    /**
     * @return the value of {@link #timeQuota}
     */
    public int getTimeQuota() {
        return timeQuota;
    }

    /**
     * @return the value of {@link #initialEnergy}
     */
    public int getInitialEnergy() {
        return initialEnergy;
    }

    /**
     * @return the value of {@link #energyLoss}
     */
    public int getEnergyLoss() {
        return energyLoss;
    }

    /**
     * @return the value of {@link #energyRefill}
     */
    public int getEnergyRefill() {
        return energyRefill;
    }

    /**
     * @return the value of {@link #energyFrequency}
     */
    public int getEnergyFrequency() {
        return energyFrequency;
    }

    /**
     * @return the value of {@link #visionRange}
     */
    public int getVisionRange() {
        return visionRange;
    }
}
