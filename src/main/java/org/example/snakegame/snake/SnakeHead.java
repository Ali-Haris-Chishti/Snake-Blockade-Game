package org.example.snakegame.snake;

import org.example.snakegame.controller.SnakeController;
import org.example.snakegame.data.Point;

// Each snake would have only one head
public class SnakeHead extends SnakeUnit{

    SnakeHead(Point point, SnakeSide side){
        super(point, side);
        SnakeController.graphicData.applyHeadGraphics(side, this);
    }

    // for logging purposes
    @Override
    public void setPoint(Point point){
        super.setPoint(point);
        String side = snakeSide == SnakeSide.SIDE_RED? "RED": "BLUE";
        System.out.println(side + " snake's head position: " + point);
    }
}
