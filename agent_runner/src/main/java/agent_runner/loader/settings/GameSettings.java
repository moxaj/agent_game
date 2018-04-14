package agent_runner.loader.settings;

/**
 * Represents the global game parameters for the simulator.
 */
public final class GameSettings {
    /**
     * The time quota for a single round of reasoning, in milliseconds.
     */
    private Integer timeQuota;

    /**
     * The initial energy of the agents.
     */
    private Integer initialEnergy;

    /**
     * The per round energy loss of the agents.
     */
    private Integer energyLoss;

    /**
     * The amount of energy refilled by energy drinks.
     */
    private Integer energyRefill;

    /**
     * The frequency of energy drink spawns.
     */
    private Integer energyFrequency;

    /**
     * The vision range of the agents.
     */
    private Integer visionRange;

    /**
     * @return the value of {@link #timeQuota}
     */
    public Integer getTimeQuota() {
        return timeQuota;
    }

    /**
     * @param timeQuota the value of {@link #timeQuota}
     */
    public void setTimeQuota(Integer timeQuota) {
        this.timeQuota = timeQuota;
    }

    /**
     * @return the value of {@link #initialEnergy}
     */
    public Integer getInitialEnergy() {
        return initialEnergy;
    }

    /**
     * @param initialEnergy the value of {@link #initialEnergy}
     */
    public void setInitialEnergy(Integer initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    /**
     * @return the value of {@link #energyLoss}
     */
    public Integer getEnergyLoss() {
        return energyLoss;
    }

    /**
     * @param energyLoss the value of {@link #energyLoss}
     */
    public void setEnergyLoss(Integer energyLoss) {
        this.energyLoss = energyLoss;
    }

    /**
     * @return the value of {@link #energyRefill}
     */
    public Integer getEnergyRefill() {
        return energyRefill;
    }

    /**
     * @param energyRefill the value of {@link #energyRefill}
     */
    public void setEnergyRefill(Integer energyRefill) {
        this.energyRefill = energyRefill;
    }

    /**
     * @return the value of {@link #energyFrequency}
     */
    public Integer getEnergyFrequency() {
        return energyFrequency;
    }

    /**
     * @param energyFrequency the value of {@link #energyFrequency}
     */
    public void setEnergyFrequency(Integer energyFrequency) {
        this.energyFrequency = energyFrequency;
    }

    /**
     * @return the value of {@link #visionRange}
     */
    public Integer getVisionRange() {
        return visionRange;
    }

    /**
     * @param visionRange the value of {@link #visionRange}
     */
    public void setVisionRange(Integer visionRange) {
        this.visionRange = visionRange;
    }
}
