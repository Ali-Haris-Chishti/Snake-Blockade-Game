package org.example.snakegame.snake;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.snakegame.controller.SnakeController;
import org.example.snakegame.data.Point;

// A snake can have a ny number of body parts
public class SnakeBodyPart extends SnakeUnit{
    public SnakeBodyPart(Point point, SnakeSide side, int alt){
        super(point, side);
        SnakeController.graphicData.applyBodyGraphics(side, this, alt);
    }
}
