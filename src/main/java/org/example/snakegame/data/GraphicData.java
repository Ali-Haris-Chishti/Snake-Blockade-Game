package org.example.snakegame.data;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.snakegame.perk.Obstacle;
import org.example.snakegame.snake.SnakeBodyPart;
import org.example.snakegame.snake.SnakeHead;
import org.example.snakegame.snake.SnakeSide;
import org.example.snakegame.view.GamePane;
import org.example.snakegame.view.StartPane;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

// This class has nothing to do with game logic, it just stores visual components of the game
public class GraphicData implements SizeData{
    private final double cornerRadius = GRID_SIZE / 3;
    private final double borderWidth = GRID_SIZE / 14;

    /* Singleton Pattern for Creating just one instance*/
    static private GraphicData instance;
    private GraphicData(){
        initializeStartBackground();
        initializeGameBackground();
        initializeSnakeHeadGraphics();
        initializeSnakeBodyGraphics();
        initializePerkBackground();
    }

    static public GraphicData getInstance(){
        if (instance == null)
            instance = new GraphicData();
        return instance;
    }

    /* Game Graphic components like Background, Border */

    private Background startBackground;

    private Background gameBackground;
    private Border gameBorder;

    private Background [] headBackground;
    private Background [][] bodyBackground;

    private Border [] headBorder;
    private Border [][] bodyBorder;

    private Background strawberryBackground;

    private Background obstacleBackground1;
    private Background obstacleBackground2;

    public Background getStrawberryBackground(){
        return strawberryBackground;
    }
    public Background getObstacleBackground1(){
        return obstacleBackground1;
    }

    private void initializeStartBackground(){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("main bg.jpg")));
        startBackground = new Background(
                new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(GRID_SIZE*X_NUM, GRID_SIZE*Y_NUM, false, false, false, false)
                )
        );
    }

    private void initializeGameBackground(){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("game background.jpg")));
        gameBackground = new Background(
                new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(GRID_SIZE* X_NUM, GRID_SIZE * Y_NUM, false, false, false, false)
                )
        );
    }

    private void initializeSnakeHeadGraphics(){
        headBackground = new Background[2];
        headBackground[0] = new Background(
                new BackgroundImage(
                        new Image(Objects.requireNonNull(getClass().getResourceAsStream("snake_head_a.png"))),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(GRID_SIZE, GRID_SIZE, false, false, false, false)
                )
        );
        headBackground[1] = new Background(
                new BackgroundImage(
                        new Image(Objects.requireNonNull(getClass().getResourceAsStream("snake_head_b.png"))),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(GRID_SIZE, GRID_SIZE, false, false, false, false)
                )
        );

        headBorder = new Border[2];
        headBorder[0] = new Border(
                new BorderStroke(
                        Color.rgb(100, 100, 100),
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(cornerRadius * 1.75),
                        new BorderWidths(borderWidth)
                )
        );
        headBorder[1] = new Border(
                new BorderStroke(
                        Color.rgb(100, 100, 100),
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(cornerRadius * 1.75),
                        new BorderWidths(borderWidth)
                )
        );
    }

    private void initializeSnakeBodyGraphics(){
        bodyBackground = new Background[2][2];
        bodyBorder = new Border[2][2];
        Color [][] colors = {{Color.rgb(250, 75, 75), Color.rgb(250, 100, 100)},
                {Color.rgb(75, 75, 250), Color.rgb(100, 100, 250)}};

        for (int i = 0; i < 2; i++)
            for (int j = 0, b = 1; j < 2; j++, b--){
                bodyBackground[i][j] = new Background(
                        new BackgroundFill(
                                colors[i][j],
                                new CornerRadii(cornerRadius),
                                null
                        )
                );
                bodyBorder[i][j] = new Border(
                        new BorderStroke(
                                Color.rgb(50, 50, 50),
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(cornerRadius),
                                new BorderWidths(borderWidth)
                        )
                );
            }
    }

    private void initializePerkBackground(){
        strawberryBackground = new Background(
                new BackgroundImage(
                        new Image(Objects.requireNonNull(getClass().getResourceAsStream("strawberry.png"))),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(GRID_SIZE, GRID_SIZE, false, false, false, false)
                )
        );
        obstacleBackground1 = new Background(
                new BackgroundImage(
                        new Image(Objects.requireNonNull(getClass().getResourceAsStream("obstacle1.png"))),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(GRID_SIZE, GRID_SIZE, false, false, false, false)
                )
        );
        obstacleBackground2 = new Background(
                new BackgroundImage(
                        new Image(Objects.requireNonNull(getClass().getResourceAsStream("obstacle2.png"))),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(GRID_SIZE, GRID_SIZE, false, false, false, false)
                )
        );
    }

    public void toggleObstacleBackground(Obstacle obstacle){
        if (obstacle.firstBg)
            obstacle.setBackground(obstacleBackground2);
        else
            obstacle.setBackground(obstacleBackground1);
        obstacle.firstBg = !obstacle.firstBg;
    }


    public void applyHeadGraphics(SnakeSide side, SnakeHead head){
        int s = 0;
        if (side == SnakeSide.SIDE_BLUE)
            s = 1;
        head.setBackground(headBackground[s]);
    }
    public void applyBodyGraphics(SnakeSide side, SnakeBodyPart bodyPart, int alt){
        int s = 0;
        if (side == SnakeSide.SIDE_BLUE)
            s = 1;
        bodyPart.setBackground(bodyBackground[s][alt]);
        bodyPart.setBorder(bodyBorder[s][alt]);
    }

    public void applyStartBackground(StartPane startPane){
        startPane.setBackground(startBackground);
    }

    public void applyGameBackground(GamePane pane){
        pane.setBackground(gameBackground);
        pane.setBorder(gameBorder);
    }

    public Background getGameBackground() {
        return gameBackground;
    }

    public void animateHead(SnakeHead head, int s){
        Timer timer = new Timer();
        final boolean[] alt = {true};
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (alt[0])
                    head.setBackground(null);
                else
                    head.setBackground(headBackground[s]);
                alt[0] = !alt[0];
            }
        }, 50, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
            }
        }, 2000);
    }
}
