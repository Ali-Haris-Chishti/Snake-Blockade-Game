package org.example.snakegame.controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import org.example.snakegame.HelloApplication;
import org.example.snakegame.data.*;
import org.example.snakegame.perk.Obstacle;
import org.example.snakegame.snake.SnakeFactory;
import org.example.snakegame.snake.SnakeSide;
import org.example.snakegame.snake.SnakeUnit;
import org.example.snakegame.perk.StrawBerry;
import org.example.snakegame.view.GameOverPane;
import org.example.snakegame.view.GamePane;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

// The main controller which  controls all the logic controllers
public class GameController implements SizeData {

    GraphicData graphicData = GraphicData.getInstance();
    GameSound gameSound = GameSound.getInstance();

    private boolean gamePaused = false;
    // Applied singleton pattern, so only one object is formed
    static private GameController instance;
    public static GameController getInstance(){
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    // variables for time check for increasing snake length
    static int timeToIncreaseSize;
    private int counter = 0;

    // Time in milliseconds after which snake will move one step, is inversely related to game speed
    private final long movementTime = 100;

    // Initializes Game Pane, the main play area
    private GameController(){
        gamePane = GamePane.getInstance();
        scene = new Scene(gamePane);
    }

    // Initializes all visual and logic components of the game
    public void initializeGame(boolean single, int time){
        timeToIncreaseSize = time;
        snakeControllerRed = SnakeControllerRed.getInstance(single);
        snakeControllerBlue = SnakeControllerBlue.getInstance();
        strawberryController = StrawberryController.getInstance();
//
        HelloApplication.getStage().setScene(getScene());
        startNewGame();
    }

    // Timer for moving the snakes
    static private Timer movementTimer;
    public void initializeMovement(){
        movementTimer = new Timer();
        movementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Changing UI components require JavaFX threads, not simple java threads, so used Platform.runLater
                Platform.runLater(()->{
                    snakeControllerBlue.snake.move(snakeControllerRed.snake.getSnake());
                    snakeControllerRed.move(snakeControllerBlue.snake);
                    counter++;
                    gamePane.requestLayout();


                    // Length increase logic
                    if (counter == timeToIncreaseSize){
                        gameSound.sizeIncrease();
                        counter = 0;
                        SnakeUnit unit1 = snakeControllerRed.snake.incrementSize();
                        SnakeUnit unit2 = snakeControllerBlue.snake.incrementSize();
                        gamePane.getChildren().addAll(unit1, unit2);
                        for (Obstacle obstacle: obstacles)
                            graphicData.toggleObstacleBackground(obstacle);
                    }
                    // This variable is for synchronization purpose
                    // so that direction is not changed twice within same move
                    snakeControllerRed.snake.hasMovedFirstTime = true;
                    snakeControllerBlue.snake.hasMovedFirstTime = true;
                });
            }
        }, movementTime, movementTime);
    }

    // Once game is over, snake will stop moving and pause menu will be showed
    static public void gameOver(SnakeSide losingSide){
        getInstance().gamePaused = true;
        if (losingSide == SnakeSide.SIDE_RED && getInstance().snakeControllerBlue.snake.gameOver)
            losingSide = null;
        else if (losingSide == SnakeSide.SIDE_BLUE && getInstance().snakeControllerRed.snake.gameOver)
            losingSide = null;
        movementTimer.cancel();
        getInstance().gamePane.getChildren().add(new GameOverPane(losingSide));
    }

    // storing and adding strawberry to gamePane, eating strawberry reduces size
    static private StrawBerry currentStrawberry;
    private static void addStrawberryInGame(){
        currentStrawberry = instance.strawberryController.generateRandomStrawberry(instance.snakeControllerRed.snake.getSnake(), instance.snakeControllerBlue.snake.getSnake());
        instance.gamePane.getChildren().add(currentStrawberry);
    }
    public static StrawBerry getCurrentStrawberry(){
        return currentStrawberry;
    }

    Vector<Obstacle> obstacles = new Vector<>();
    public Vector<Obstacle> getObstacles(){
        return obstacles;
    }
    private void addObstaclesToGame(){
        obstacles.clear();
        for (int i = 0; i < 15; i++) {
            obstacles.add(generateRandomObstacle());
            gamePane.getChildren().add(obstacles.get(i));
        }
    }
    private Obstacle generateRandomObstacle(){
        Random random = new Random();
        Point point = new Point(1, 1);
        boolean loop = true;
        while (loop){
            loop = false;
            point = new Point(random.nextInt(5, X_NUM - 6), random.nextInt(5, Y_NUM - 6));
            for (Obstacle obstacle: obstacles)
                if (obstacle.getPoint().pointEquals(point))
                    loop = true;
            for (SnakeUnit unit: getInstance().snakeControllerRed.snake.getSnake())
                if (unit.getPoint().pointEquals(point))
                    loop = true;
            for (SnakeUnit unit: getInstance().snakeControllerBlue.snake.getSnake())
                if (unit.getPoint().pointEquals(point))
                    loop = true;
            if (point.pointEquals(currentStrawberry.getPoint()))
                loop = true;
        }
        return new Obstacle(point);
    }

    // Initializing controls for both snakes, W,A,S,D would not work in single player mode
    private void initializeControls() {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case W ->{
                    if (!snakeControllerRed.isSinglePlayer())
                        snakeControllerRed.snake.setDirection(Direction.UP);
                }
                case D ->{
                    if (!snakeControllerRed.isSinglePlayer())
                        snakeControllerRed.snake.setDirection(Direction.RIGHT);
                }
                case S ->{
                    if (!snakeControllerRed.isSinglePlayer())
                        snakeControllerRed.snake.setDirection(Direction.DOWN);
                }
                case A ->{
                    if (!snakeControllerRed.isSinglePlayer())
                        snakeControllerRed.snake.setDirection(Direction.LEFT);
                }
                case UP ->{
                    snakeControllerBlue.snake.setDirection(Direction.UP);
                }
                case RIGHT ->{
                    snakeControllerBlue.snake.setDirection(Direction.RIGHT);
                }
                case DOWN ->{
                    snakeControllerBlue.snake.setDirection(Direction.DOWN);
                }
                case LEFT ->{
                    snakeControllerBlue.snake.setDirection(Direction.LEFT);
                }
                case ENTER -> {
                    System.out.println("ENTER");
                    if (gamePaused)
                        startNewGame();
                }
                case ESCAPE -> {
                    if (gamePaused)
                        System.exit(0);
                }
            }
        });
    }

    private final GamePane gamePane;
    private final Scene scene;

    private SnakeControllerRed snakeControllerRed;
    private SnakeControllerBlue snakeControllerBlue;

    private StrawberryController strawberryController;

    public Scene getScene(){
        return scene;
    }

    // Reducing snake size after eating strawberry
    static public void removeBodyPart(SnakeUnit unit){
        instance.gamePane.getChildren().remove(unit);
        instance.gamePane.getChildren().remove(currentStrawberry);
        addStrawberryInGame();
    }

    // Reinitializing game components if user chooses to play again
    public  void startNewGame(){
        gamePane.getChildren().clear();
        snakeControllerRed.snake = SnakeFactory.getInstance().getSnake(SnakeSide.SIDE_RED);
        snakeControllerBlue.snake = SnakeFactory.getInstance().getSnake(SnakeSide.SIDE_BLUE);
        snakeControllerRed.addSnakesToPane(gamePane);
        snakeControllerBlue.addSnakesToPane(gamePane);
        initializeControls();
        initializeMovement();
        addStrawberryInGame();
        addObstaclesToGame();
        gamePaused = false;
    }
}
