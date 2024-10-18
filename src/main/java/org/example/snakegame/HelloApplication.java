package org.example.snakegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.snakegame.view.StartPane;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Initializing Start pane asking game options
        StartPane startPane = new StartPane();
        stage.setScene(new Scene(startPane));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        HelloApplication.stage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}