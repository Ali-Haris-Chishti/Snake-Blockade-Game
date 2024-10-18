package org.example.snakegame.snake;

import javafx.scene.control.Label;
import org.example.snakegame.data.GraphicData;
import org.example.snakegame.data.Point;
import org.example.snakegame.data.SizeData;

// A base class for a basic snake unit, head or a body part
public class SnakeUnit extends Label implements SizeData {
    protected Point point;
    protected SnakeSide snakeSide;
    public SnakeUnit(Point point, SnakeSide side){
        setPrefSize(GRID_SIZE, GRID_SIZE);
        setLayoutX(point.getLayoutXValue());
        setLayoutY(point.getLayoutYValue());
        setPoint(point);
        snakeSide = side;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
        setLayoutX(point.getLayoutXValue());
        setLayoutY(point.getLayoutYValue());
    }
}
