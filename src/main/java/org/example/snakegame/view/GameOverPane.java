package org.example.snakegame.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.snakegame.controller.GameController;
import org.example.snakegame.data.SizeData;
import org.example.snakegame.snake.SnakeSide;

import java.util.Objects;

// This pane shows up over the game pane once someone loses the game, asking for further option
public class GameOverPane extends Pane implements SizeData {
    public GameOverPane(SnakeSide losingSide){
        setPrefSize(GRID_SIZE * X_NUM /2, GRID_SIZE * Y_NUM / 2);
        setLayoutX(GRID_SIZE * X_NUM / 4);
        setLayoutY(GRID_SIZE *Y_NUM / 4);
        setBackground(
                new Background(
                        new BackgroundFill(
                                Color.rgb(0, 0, 0, 0.4),
                                new CornerRadii(5),
                                null
                        )
                )
        );
        getChildren().add(getGameOverComponents(losingSide));
    }

    // This displays the winner
    VBox getGameOverComponents(SnakeSide side){
        VBox vBox = new VBox(getPlayerWinLabel(side == SnakeSide.SIDE_RED? "BLUE": "RED"), getOptionButtons());
        // null will be sent if both snakes equivalently do losing condition
        if (side == null) {
            Label label = new Label("Game Tied!");
            label.setFont(new Font("Times New Roman BOLD", 20));
            label.setAlignment(Pos.CENTER);
            label.setTextFill(Color.FLORALWHITE);
            HBox hBox = new HBox(label);
            hBox.setAlignment(Pos.CENTER);
            vBox = new VBox(hBox, getOptionButtons());
        }
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        vBox.setPrefSize(GRID_SIZE * X_NUM /2, GRID_SIZE * Y_NUM / 2);

        return vBox;
    }

    // Returns the winning text
    HBox getPlayerWinLabel(String color){
        Label label = new Label("Player " + color + " Wins!");
        label.setStyle("-fx-font-family: 'Arial Black';" +
                "-fx-font-weight: BOLD;" +
                "-fx-font-size: 30px;");
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Objects.equals(color, "RED") ? Color.RED: Color.BLUE);
        HBox hBox = new HBox(label);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    // Adds play again and exit options to pause menu
    VBox getOptionButtons(){

        buttonBackground =  new Background(
                new BackgroundFill(
                        Color.rgb(108, 116, 121),
                        null,
                        null
                )
        );
        onHoverBackground =  new Background(
                new BackgroundFill(
                        Color.rgb(128, 136, 131),
                        null,
                        null
                )
        );

        HBox hBox1 = new HBox(getStyledButton("Play Again"));
        hBox1.setAlignment(Pos.CENTER);

        HBox hBox2 = new HBox(getStyledButton("Main Screen"));
        hBox2.setAlignment(Pos.CENTER);

        HBox hBox3 = new HBox(getStyledButton("Leave"));
        hBox3.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(hBox1, hBox2, hBox3);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        return vBox;
    }

    Background buttonBackground;
    Background onHoverBackground;

    Button getStyledButton(String text){
        Button button = new Button(text);
        button.setAlignment(Pos.CENTER);
        button.setPrefSize(250, 50);
        button.setFont(new Font("Cambria", 20));
        button.setPadding(new Insets(10, 8, 10, 8));
        button.setBackground(buttonBackground);
        button.setTextFill(Color.WHITE);
        button.setCursor(Cursor.HAND);
        // Logic for button click of both buttons

        button.setOnAction((actionEvent -> {
            if (text.equals("Leave"))
                System.exit(0);
            else if (text.equals("Main Screen")){
                StartPane.goToStartScreen();
            }
            else {
                GameController.getInstance().startNewGame();
            }
        }));

        button.setOnMouseEntered(actionEvent -> button.setBackground(onHoverBackground));
        button.setOnMouseExited(actionEvent -> button.setBackground(buttonBackground));
        return button;
    }
}
