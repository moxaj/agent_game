# Prerequisites

For developers:
- JDK 8+
- Gradle
- IntelliJ IDEA

For users:
- JRE 7+

# Dev setup

First, clone the repository: `git clone https://github.com/moxaj/agent_project.git`.

Then, either run the application / tests via gradle commands, or import the project into IDEA (`Import project` \ `Gradle`).
   
# Running the simulator

## Runner mode

The application can be launched with 2 modes, set via command line arguments.

To launch in headless mode, pass `--mode headless --settings <relative-path-to-settings.yaml>`.

To launch with a GUI, pass `--mode javafx`.

## Launch

The application can be launched either:
- via IDEA: the main class is `agent_runner.launcher.Launcher` (don't forget to set the args in its configuration)
- via the terminal:
  1. bundle the app with `gradle :agent_runner:shadowJar`
  2. launch it with `java -jar ./agent_runner/build/libs/agent-app.jar <args>`

# Running compiler tests

The tests can be run either:
- via IDEA: the main class is `agent_script.compiler.CompilerTest`
- via the terminal: `gradle :agent_script:test`

# Documentation

See the [wiki](https://github.com/moxaj/agent_project/wiki).
