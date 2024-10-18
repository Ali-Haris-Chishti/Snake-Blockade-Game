package org.example.snakegame.controller;

import org.example.snakegame.data.Direction;
import org.example.snakegame.snake.Snake;

public interface AIImplementation {
    public Direction getNextBestDirection(Snake snake);
}
