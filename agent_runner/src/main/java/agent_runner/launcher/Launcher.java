package agent_runner.launcher;

import agent_runner.runner.Runner;
import agent_runner.runner.headless.HeadlessRunner;
import agent_runner.runner.javafx.JavaFxRunner;
import org.apache.commons.cli.*;

import java.nio.file.Paths;

public class Launcher {
    private static Options makeOptions() {
        Options options = new Options();

        Option modeOption = Option
                .builder("m")
                .longOpt("mode")
                .required(false)
                .numberOfArgs(1)
                .desc("the mode to use ('headless' or 'javafx', defaults to 'headless')")
                .build();
        options.addOption(modeOption);

        Option settingsOption = Option
                .builder("s")
                .longOpt("settings")
                .required(false)
                .numberOfArgs(1)
                .desc("the path to the simulator settings to use in headless mode (defaults to ./settings.yaml)")
                .build();
        options.addOption(settingsOption);

        return options;
    }

    private static Runner makeRunner(RunnerMode runnerMode, CommandLine commandLine) {
        switch (runnerMode) {
            case HEADLESS:
                return new HeadlessRunner(Paths.get(commandLine.getOptionValue("s", "./settings.yaml")));
            case JAVA_FX:
                return new JavaFxRunner();
            default:
                // Should not happen
                throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        Options options = makeOptions();

        CommandLine commandLine;
        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("utility-name", options);
            System.exit(1);
            return;
        }

        String runnerModeStr = commandLine.getOptionValue("m", "headless");
        RunnerMode runnerMode = RunnerMode.parse(runnerModeStr);
        if (runnerMode == null) {
            System.out.println(String.format("Invalid 'mode' argument: %s (expected 'headless' or 'javafx')", runnerModeStr));
            System.exit(1);
            return;
        }

        makeRunner(runnerMode, commandLine).run();
    }

    private enum RunnerMode {
        HEADLESS, JAVA_FX;

        public static RunnerMode parse(String s) {
            if ("headless".equals(s)) {
                return HEADLESS;
            } else if ("javafx".equals(s)) {
                return JAVA_FX;
            } else {
                return null;
            }
        }
    }
}
