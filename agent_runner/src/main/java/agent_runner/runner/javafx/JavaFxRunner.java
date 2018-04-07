package agent_runner.runner.javafx;

import agent_runner.loader.LoaderException;
import agent_runner.runner.Runner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class JavaFxRunner extends Application implements Runner {
    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("javafx runner started");

        FXMLLoader loader = new FXMLLoader(JavaFxRunner.class.getResource("application.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (LoaderException e) {
            System.out.println(String.format("runner crashed with exception: %s", e.getMessage()));
            System.out.println("full stacktrace: ");
            e.printStackTrace();
            System.exit(1);
            return;
        }

        Scene scene = new Scene(root, 1200, 820);
        primaryStage.setTitle("Agent game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            ((MainController) loader.getController()).onStageClosed();
            System.out.println("runner finished");
        });
        primaryStage.show();
    }
}
