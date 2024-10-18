package org.example.snakegame.snake;

import org.example.snakegame.controller.GameController;
import org.example.snakegame.data.Direction;
import org.example.snakegame.data.GraphicData;
import org.example.snakegame.data.Point;
import org.example.snakegame.data.SizeData;
import org.example.snakegame.perk.Obstacle;
import org.example.snakegame.perk.StrawBerry;

import java.util.Vector;

// This class constitutes all of its components to make a snake
public class Snake {
    public boolean gameOver = false;
    SnakeSide snakeSide;

    // All points in the snake, where first one is its head
    final private Vector<SnakeUnit> snakeBody = new Vector<>();

    public int getSize(){
        return snakeBody.size();
    }
    Direction direction;

    // used to store the point where tail was present in the last movement, it is the point where when size is increased, the new body part is added
    Point lastPoint;


    // Constructor generating an initial snake for both of snake types
    Snake(SnakeSide side){
        this.snakeSide = side;
        if (side == SnakeSide.SIDE_RED) {
            direction = Direction.UP;
            snakeBody.add(new SnakeHead(new Point(3, 8), side));
            for (int i = 9; i < 14; i++)
                snakeBody.add(new SnakeBodyPart(new Point(3, i), side, snakeBody.size() % 2));
        }
        else {
            direction = Direction.DOWN;
            snakeBody.add(new SnakeHead(new Point(SizeData.X_NUM - 3 - 1, 10), side));
            for (int i = 9; i > 3; i--)
                snakeBody.add(new SnakeBodyPart(new Point(SizeData.X_NUM - 3 - 1, i), side, snakeBody.size() % 2));
        }

    }

    // function called when size is to be increased
    public SnakeUnit incrementSize(){
        System.out.println("Increasing Size!");
        snakeBody.add(new SnakeBodyPart(lastPoint, snakeSide, snakeBody.size() % 2));
        return snakeBody.getLast();
    }

    // function called when size is to be decreased
    public void decrementSize(){
        System.out.println("Decreasing Size!");
        GameController.removeBodyPart(snakeBody.removeLast());
    }

    public Vector<SnakeUnit> getSnake(){
        return snakeBody;
    }

    // function to move the snake in the current direction
    public void move(Vector<SnakeUnit> oppositeBody) {
        SnakeUnit head = snakeBody.getFirst();
        Point point = head.point.getNextPoint(direction);

        // checking if in the current direction, is it safe to move, if not than game is over for that snake
        if (!isSafe(point, oppositeBody)){
            GraphicData.getInstance().animateHead((SnakeHead) snakeBody.getFirst(), snakeSide == SnakeSide.SIDE_RED? 0: 1);
            gameOver = true;
            GameController.gameOver(snakeSide);
            return;
        }
        // Storing lastPoint value as tail is now moving to next point
        lastPoint = snakeBody.getLast().getPoint();
        // Moving all snake body parts to the position where its previous body part was present
        for (int i = snakeBody.size() - 1; i > 0; i--)
            snakeBody.get(i).setPoint(snakeBody.get(i - 1).point);

        if (isStrawberryEaten(point))
            decrementSize();

        head.setPoint(point);
    }

    public boolean isSafe(Point point, Vector<SnakeUnit> oppositeBody){
        // Boundary Collision condition
        if (point.isNotSafeForBoundaries())
            return false;
        // Collision with itself condition
        for (SnakeUnit unit: getExposedBody())
            if (unit.getPoint().pointEquals(point)) {
                System.out.println("Equal Point: " + unit.getPoint());
                return false;
            }
        // Collision of head with opponent condition
        for (SnakeUnit unit: oppositeBody)
            if (unit.getPoint().pointEquals(point))
                return false;
        // Collision condition with any obstacle
        for (Obstacle obstacle: GameController.getInstance().getObstacles())
            if (obstacle.getPoint().pointEquals(point))
                return false;
        return true;
    }

    public boolean isStrawberryEaten(Point point){
        StrawBerry strawBerry = GameController.getCurrentStrawberry();
        return point.pointEquals(strawBerry.getPoint());
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean hasMovedFirstTime = false;

    // sets the direction if it is safe
    public void setDirection(Direction dir) {
        if (!hasMovedFirstTime)
            return;
        switch (dir){
            case DOWN, UP ->{
                if (!isSafeForUpDown())
                    return;
            }
            case LEFT, RIGHT ->{
                if (!isSafeForLeftRight())
                    return;
            }
        }
        this.direction = dir;
        hasMovedFirstTime = false;
    }

    // check safety condition for direction change, as direction can not be changed from current direction to a direction which is equal or opposite to current direction
    public boolean isSafeForLeftRight(){
        return direction == Direction.UP || direction == Direction.DOWN;
    }

    public boolean isSafeForUpDown(){
        return direction == Direction.LEFT || direction == Direction.RIGHT;
    }

    public Point getHeadPoint(){
        return snakeBody.getFirst().point;
    }

    // returns the snake except its first three body points, as snake head can not collide with first three points of itself including his own head
    public Vector<SnakeBodyPart> getExposedBody(){
        Vector<SnakeBodyPart> body = new Vector<>();
        for (int i = 3; i < snakeBody.size(); i++)
            body.add((SnakeBodyPart) snakeBody.get(i));

        return body;
    }
}
