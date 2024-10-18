package org.example.snakegame.perk;

import javafx.scene.control.Label;
import org.example.snakegame.data.GraphicData;
import org.example.snakegame.data.Point;
import org.example.snakegame.data.SizeData;

public class Obstacle extends Label implements SizeData {
    Point point;
    public boolean firstBg = true;
    public Obstacle(Point point){
        setPrefSize(GRID_SIZE, GRID_SIZE);
        setPoint(point);
        setBackground(GraphicData.getInstance().getObstacleBackground1());
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
