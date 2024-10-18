package org.example.snakegame.perk;

import javafx.scene.control.Label;
import org.example.snakegame.data.GraphicData;
import org.example.snakegame.data.Point;
import org.example.snakegame.data.SizeData;

// Eating strawberry decreases snake's length by one
public class StrawBerry extends Label implements SizeData {
    private Point point;
    public StrawBerry(Point point){
        setPrefSize(GRID_SIZE, GRID_SIZE);
        setBackground(GraphicData.getInstance().getStrawberryBackground());
        setPoint(point);
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
