package org.example.snakegame.controller;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import org.example.snakegame.data.GraphicData;
import org.example.snakegame.snake.Snake;
import org.example.snakegame.snake.SnakeFactory;
import org.example.snakegame.snake.SnakeSide;
import org.example.snakegame.snake.SnakeUnit;
import org.example.snakegame.view.GamePane;

// Base class for both controllers of both snakes
public abstract class SnakeController {

    // Each controller will have its snake to control
    protected Snake snake;

    public static GraphicData graphicData = GraphicData.getInstance();


    // Snake will be initialized through SnakeFactory
    protected SnakeController(SnakeSide side){
        SnakeFactory factory = SnakeFactory.getInstance();

        snake = factory.getSnake(side);

        addSnakesToPane(GamePane.getInstance());
    }


    // Adding created snakes to the gamePane, so they become visible
    public void addSnakesToPane(GamePane gamePane){
        for (SnakeUnit unit: snake.getSnake()) {
            gamePane.getChildren().add(unit);
        }
    }

    // abstract method with implementations in child classes to check whether direction change request is valid or invalid
    public abstract boolean isValidDirection(KeyCode code);
}
