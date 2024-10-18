package org.example.snakegame.controller;

import org.example.snakegame.data.Point;
import org.example.snakegame.data.SizeData;
import org.example.snakegame.snake.SnakeUnit;
import org.example.snakegame.perk.StrawBerry;

import java.util.Random;
import java.util.Vector;

// controller class for strawberry generation and logic
public class StrawberryController implements SizeData {
    // Applied singleton pattern, so only one object is formed
    private static StrawberryController instance;
    private StrawberryController(){

    }
    public static StrawberryController getInstance(){
        if (instance == null)
            instance = new StrawberryController();
        return instance;
    }

    public StrawBerry generateRandomStrawberry(Vector<SnakeUnit> snakeRed, Vector<SnakeUnit> snakeBlue){
        boolean repeat;
        Point point;
        do {
            repeat = false;
            Random random = new Random();
            point = new Point(random.nextInt(1, X_NUM - 1), random.nextInt(1, Y_NUM - 1));
            for (SnakeUnit unit: snakeRed)
                if (unit.getPoint().pointEquals(point)){
                    repeat = true;
                    continue;
                }
            for (SnakeUnit unit: snakeBlue)
                if (unit.getPoint().pointEquals(point)){
                    repeat = true;
                    continue;
                }
        }while (repeat);
        System.out.println("returning strawberry at : " + point);
        return new StrawBerry(point);
    }
}
