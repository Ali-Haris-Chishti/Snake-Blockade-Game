package org.example.snakegame.controller;

import javafx.scene.input.KeyCode;
import org.example.snakegame.snake.SnakeSide;

// Controller for blue snake
public class SnakeControllerBlue extends SnakeController{
    // Applied singleton pattern, so only one object is formed
    private static SnakeControllerBlue instance;
    public static SnakeControllerBlue getInstance(){
        if (instance == null)
            instance = new SnakeControllerBlue();
        return instance;
    }
    private SnakeControllerBlue(){
        super(SnakeSide.SIDE_BLUE);
    }

    // If snake is in Up or Down direction then, requesting the direction to change to Up or down is not valid, only left or right is valid and vice versa
    @Override
    public boolean isValidDirection(KeyCode code){
        switch (code){
            case UP, DOWN -> {
                return snake.isSafeForUpDown();
            }
            case LEFT , RIGHT -> {
                return snake.isSafeForLeftRight();
            }
            default -> {
                return false;
            }
        }
    }
}
