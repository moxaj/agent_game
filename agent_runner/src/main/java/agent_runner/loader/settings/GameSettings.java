package agent_runner.loader.settings;

/**
 * Represents the global game parameters for the simulator.
 */
public class GameSettings {
    /**
     * The time quota for a single round of reasoning, in milliseconds.
     */
    private int timeQuota;

    /**
     * The initial energy of the agents.
     */
    private int initialEnergy;

    /**
     * The per round energy loss of the agents.
     */
    private int energyLoss;

    /**
     * The amount of energy refilled by energy drinks.
     */
    private int energyRefill;

    /**
     * The frequency of energy drink spawns.
     */
    private int energyFrequency;

    /**
     * The vision range of the agents.
     */
    private int visionRange;

    /**
     * @return the value of {@link #timeQuota}
     */
    public int getTimeQuota() {
        return timeQuota;
    }

    /**
     * @param timeQuota the value of {@link #timeQuota}
     */
    public void setTimeQuota(int timeQuota) {
        this.timeQuota = timeQuota;
    }

    /**
     * @return the value of {@link #initialEnergy}
     */
    public int getInitialEnergy() {
        return initialEnergy;
    }

    /**
     * @param initialEnergy the value of {@link #initialEnergy}
     */
    public void setInitialEnergy(int initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    /**
     * @return the value of {@link #energyLoss}
     */
    public int getEnergyLoss() {
        return energyLoss;
    }

    /**
     * @param energyLoss the value of {@link #energyLoss}
     */
    public void setEnergyLoss(int energyLoss) {
        this.energyLoss = energyLoss;
    }

    /**
     * @return the value of {@link #energyRefill}
     */
    public int getEnergyRefill() {
        return energyRefill;
    }

    /**
     * @param energyRefill the value of {@link #energyRefill}
     */
    public void setEnergyRefill(int energyRefill) {
        this.energyRefill = energyRefill;
    }

    /**
     * @return the value of {@link #energyFrequency}
     */
    public int getEnergyFrequency() {
        return energyFrequency;
    }

    /**
     * @param energyFrequency the value of {@link #energyFrequency}
     */
    public void setEnergyFrequency(int energyFrequency) {
        this.energyFrequency = energyFrequency;
    }

    /**
     * @return the value of {@link #visionRange}
     */
    public int getVisionRange() {
        return visionRange;
    }

    /**
     * @param visionRange the value of {@link #visionRange}
     */
    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }
}
