package org.example.snakegame.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.snakegame.HelloApplication;
import org.example.snakegame.controller.GameController;
import org.example.snakegame.data.GraphicData;
import org.example.snakegame.data.SizeData;

public class StartPane extends Pane implements SizeData {
    GraphicData graphicData = GraphicData.getInstance();
    Background buttonBackground;
    Background onHoverBackground;


    int time;
    public StartPane(){
        setPrefSize(GRID_SIZE * X_NUM, GRID_SIZE * Y_NUM);
        buttonBackground = new Background(
                new BackgroundFill(
                        Color.rgb(247, 247, 120),
                        new CornerRadii(5),
                        null
                )
        );
        onHoverBackground = new Background(
                new BackgroundFill(
                        Color.rgb(220, 220, 150),
                        new CornerRadii(5),
                        null
                )
        );
        initializeStartPane();
    }

    // Adding all options to the pane for display
    private void initializeStartPane(){
        graphicData.applyStartBackground(this);

        Button single = createStyledButton("SINGLE PLAYER");
        single.setLayoutX((GRID_SIZE*X_NUM - 350)/2);
        single.setLayoutY(400);

        Button multi = createStyledButton("MULTI PLAYER");
        multi.setLayoutX((GRID_SIZE*X_NUM - 350)/2);
        multi.setLayoutY(500);

//        Pane pane = getLengthIncreaseTime();
//        pane.setLayoutX((GRID_SIZE*X_NUM - 300)/2);
//        pane.setLayoutY(600);

        // The formula for setLayoutX() is centering the options

        this.getChildren().addAll(single, multi, getLevelSlider());
    }

    // This function returns a styled button according to our requirement
    private Button createStyledButton(String title){
        Button button = new Button(title);
        button.setStyle("-fx-font-weight: bold;" +
                "-fx-font-family: Bahnschrift;" +
                "-fx-font-size: 32px;");
        button.setPrefSize(350, 50);
        button.setBackground(buttonBackground);
        button.setTextFill(Color.rgb(120, 189, 62));
        button.setCursor(Cursor.HAND);

        button.setOnMouseEntered((mouseEvent)->{
            button.setBackground(onHoverBackground);
            button.setTextFill(Color.rgb(100, 199, 52));
        });

        button.setOnMouseExited((mouseEvent)->{
            button.setBackground(buttonBackground);
            button.setTextFill(Color.rgb(120, 189, 62));
        });

        // Logic for any of the buttons clicked
        button.setOnMousePressed((mouseEvent -> {
            GameController controller = GameController.getInstance();
            controller.initializeGame(button.getText().equals("SINGLE PLAYER"), getIncreaseTimeBasedOnDifficultyValue());
        }));

        return button;
    }

    // Combo box to take from user, the iterations after which size will increase
    private Pane getLengthIncreaseTime(){
        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setStyle(
                "-fx-background-color: rgb(80, 50, 230);" +
                        "-fx-font-family: 'Noto Sans Tifinagh Agraw Imazighen' 20;" +
                        "-fx-text-fill: white;"
        );

        for (int i = 5; i <= 10; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.setValue(7);
        time = 7;
        comboBox.setOnAction((actionEvent -> {
            time = comboBox.getValue();
        }));

        Label label = new Label("Length Increase Time: ");
        label.setFont(new Font("Times New Roman BOLD", 18));
        label.setTextFill(Color.rgb(80, 50, 230));

        HBox hBox = new HBox(label, comboBox);

        return new Pane(hBox);
    }

    Slider slider;
    private VBox getLevelSlider(){
        slider = new Slider(6, 15, 10);

        slider.setPrefWidth(300);
        slider.setLayoutX(25);

        slider.getStylesheets().add(getClass().getResource("slider-style.css").toExternalForm());

        Label level = new Label("Difficulty");
        level.setStyle("-fx-text-fill: rgb(120, 189, 62);" +
                "-fx-font-size: 26px;" +
                "-fx-font-weight: BOLD;" +
                "-fx-font-family: Bahnschrift;");
        level.setPrefWidth(350);
        level.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(level, slider);
        vBox.setLayoutX((GRID_SIZE*X_NUM - 350)/2);
        vBox.setLayoutY(600);
        vBox.setPrefSize(350, 75);
        vBox.setStyle("-fx-background-color: rgb(247, 247, 120);");
        vBox.setPadding(new Insets(10));

        return vBox;
    }

    private int getIncreaseTimeBasedOnDifficultyValue(){
        double value = slider.getValue();
        return (int) (21 - value);
    }

    public static void goToStartScreen(){
        StartPane startPane = new StartPane();
        Stage stage = HelloApplication.getStage();
        stage.setScene(new Scene(startPane));
        stage.show();
    }
}
