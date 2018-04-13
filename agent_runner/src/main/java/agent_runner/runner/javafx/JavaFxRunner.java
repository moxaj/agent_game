package agent_runner.runner.javafx;

import agent_runner.runner.Runner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class JavaFxRunner extends Application implements Runner {
    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("javafx runner started");

        FXMLLoader loader = new FXMLLoader(JavaFxRunner.class.getResource("application.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

        primaryStage.setTitle("Agent game");
        primaryStage.setScene(new Scene(root, 1200, 820));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            ((MainController) loader.getController()).onStageClosed();
            System.out.println("runner finished");
        });
        primaryStage.show();
    }
}
