package agent_runner.runner.headless;

import agent_game.simulator.Simulator;
import agent_runner.loader.DefaultLoader;
import agent_runner.loader.LoaderException;
import agent_runner.runner.Runner;

import java.nio.file.Path;

/**
 * A {@link Runner} implementation which provides no visuals.
 */
public final class HeadlessRunner implements Runner {
    /**
     * The path to the settings file.
     */
    private final Path settingsPath;

    public HeadlessRunner(Path settingsPath) {
        this.settingsPath = settingsPath;
    }

    @Override
    public void run() {
        System.out.println("headless runner started");

        Simulator simulator;
        try {
            simulator = new DefaultLoader().load(settingsPath);
        } catch (LoaderException e) {
            System.out.println(String.format("runner crashed with exception: %s", e.getMessage()));

            System.out.println("Full stacktrace: ");
            e.printStackTrace();

            System.exit(1);
            return;
        }

        simulator.reset();
        while (!simulator.getGameState().isFinished()) {
            simulator.step();

            // Visual indicator that the simulation is still running
            if (simulator.getGameState().getRound() % 10000 == 0) {
                System.out.print(".");
            }
        }

        System.out.println("runner finished");
    }
}
