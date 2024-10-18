package org.example.snakegame.controller;

import javafx.scene.input.KeyCode;
import org.example.snakegame.data.Direction;
import org.example.snakegame.data.Point;
import org.example.snakegame.perk.Obstacle;
import org.example.snakegame.snake.Snake;
import org.example.snakegame.snake.SnakeBodyPart;
import org.example.snakegame.snake.SnakeSide;
import org.example.snakegame.snake.SnakeUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.random.RandomGenerator;

// Controller for Red snake which has both functionality for human player and AI
public class SnakeControllerRed extends SnakeController implements AIImplementation{

    private static boolean singlePlayer;
    public static void setSinglePlayer(boolean singlePlayer){
        SnakeControllerRed.singlePlayer = singlePlayer;
    }

    // Applied singleton pattern, so only one object is formed
    private static SnakeControllerRed instance;
    public static SnakeControllerRed getInstance(boolean single){
        if (instance == null)
            instance = new SnakeControllerRed();
        SnakeControllerRed.singlePlayer = single;
        return instance;
    }
    private SnakeControllerRed(){
        super(SnakeSide.SIDE_RED);
    }

    // If snake is in Up or Down direction then, requesting the direction to change to Up or down is not valid, only left or right is valid and vice versa
    @Override
    public boolean isValidDirection(KeyCode code){
        switch (code){
            case W, S -> {
                return snake.isSafeForUpDown();
            }
            case A , D -> {
                return snake.isSafeForLeftRight();
            }
            default -> {
                return false;
            }
        }
    }

    public boolean isSinglePlayer(){
        return singlePlayer;
    }

    @Override
    public Direction getNextBestDirection(Snake oppositeSnake) {


        Point point;
        Direction direction;
        Vector<Direction> directions = new Vector<>();
        // snake is more likely to continue its current direction rather than changing, if next block is safe
        directions.add(snake.getDirection());
        directions.add(snake.getDirection());
        directions.add(snake.getDirection());
        directions.add(Direction.UP);
        directions.add(Direction.RIGHT);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
        directions.add(snake.getDirection());
        directions.add(snake.getDirection());
        directions.add(snake.getDirection());
        // Counter variable to set direction if not found safe in a number of attempts
        int i = 0;
        while (true){
            direction = directions.get(RandomGenerator.getDefault().nextInt(0, directions.size()));
            point = snake.getHeadPoint().getNextPoint(direction);
            if (isSafe(point, oppositeSnake)) {
                if (isSafeForOppositeDirection(direction)){
                    return direction;
                }
            }
            i++;
            if (i == 40) {
                return snake.getDirection();
            }
        }
    }

    // Safe point logic for single player game
    private boolean isSafe(Point point, Snake oppositeSnake) {
        // Checking for boundary collision
        if (point.isNotSafeForBoundaries())
            return false;
        // Checking for collision with itself
        for (SnakeBodyPart bodyPart: snake.getExposedBody())
            if (bodyPart.getPoint().pointEquals(point))
                return false;
        // checking collision with opponent snake
        for (SnakeUnit unit: oppositeSnake.getSnake())
            if (unit.getPoint().pointEquals(point))
                return false;
        // Checking for collision with obstacle
        for (Obstacle obstacle: GameController.getInstance().obstacles)
            if (obstacle.getPoint().pointEquals(point))
                return false;
        return true;
    }

    // finds next best direction and moves the snake
    public void move(Snake oppSnake) {
        if (singlePlayer)
            snake.setDirection(getNextBestDirection(oppSnake));
        snake.move(oppSnake.getSnake());
    }

    // direction can not be changed into its opposite one directly, for example snake can not move down directly from moving up
    public boolean isSafeForOppositeDirection(Direction direction){
        switch (snake.getDirection()){
            case UP ->{
                if (direction == Direction.DOWN)
                    return false;
            }
            case DOWN ->{
                if (direction == Direction.UP)
                    return false;
            }
            case LEFT ->{
                if (direction == Direction.RIGHT)
                    return false;
            }
            case RIGHT ->{
                if (direction == Direction.LEFT)
                    return false;
            }
        }
        return true;
    }
}
