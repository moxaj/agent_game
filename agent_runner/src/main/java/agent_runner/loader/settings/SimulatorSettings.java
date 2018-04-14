package agent_runner.loader.settings;

/**
 * Represents the simulator settings, including all game settings.
 */
public final class SimulatorSettings {
    /**
     * The team settings.
     */
    private TeamSettings[] teams;

    /**
     * The arena settings.
     */
    private ArenaSettings arenaSettings;

    /**
     * The settings related to simulator and agent statistics.
     */
    private StatisticsSettings statisticsSettings;

    /**
     * The game settings.
     */
    private GameSettings gameSettings;

    /**
     * @return the value of {@link #teams}
     */
    public TeamSettings[] getTeams() {
        return teams;
    }

    /**
     * @param teams the value of {@link #teams}
     */
    public void setTeams(TeamSettings[] teams) {
        this.teams = teams;
    }

    /**
     * @return the value of {@link #arenaSettings}
     */
    public ArenaSettings getArenaSettings() {
        return arenaSettings;
    }

    /**
     * @param arenaSettings the value of {@link #arenaSettings}
     */
    public void setArenaSettings(ArenaSettings arenaSettings) {
        this.arenaSettings = arenaSettings;
    }

    /**
     * @return the value of {@link #statisticsSettings}
     */
    public StatisticsSettings getStatisticsSettings() {
        return statisticsSettings;
    }

    /**
     * @param statisticsSettings the value of {@link #statisticsSettings}
     */
    public void setStatisticsSettings(StatisticsSettings statisticsSettings) {
        this.statisticsSettings = statisticsSettings;
    }

    /**
     * @return the value of {@link #gameSettings}
     */
    public GameSettings getGameSettings() {
        return gameSettings;
    }

    /**
     * @param gameSettings the value of {@link #gameSettings}
     */
    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }
}
