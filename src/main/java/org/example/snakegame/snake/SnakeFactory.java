package org.example.snakegame.snake;

import org.example.snakegame.data.SizeData;

public class SnakeFactory implements SizeData {
    // Applied Singleton pattern so only one instance could be formed
    static private SnakeFactory factory;
    private SnakeFactory(){
    }
    private Snake initailizeSnake(SnakeSide side){
        return new Snake(side);
    }
    static public SnakeFactory getInstance(){
        if (factory == null)
            factory = new SnakeFactory();
        return factory;
    }

    public Snake getSnake(SnakeSide side){
        return initailizeSnake(side);
    }
}
