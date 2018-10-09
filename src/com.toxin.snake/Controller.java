package com.toxin.snake;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Controller implements EventHandler<KeyEvent> {

    private Snake snake;

    public Controller(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case UP: snake.setDirection(SnakeDirection.UP); break;
            case DOWN: snake.setDirection(SnakeDirection.DOWN); break;
            case LEFT: snake.setDirection(SnakeDirection.LEFT); break;
            case RIGHT: snake.setDirection(SnakeDirection.RIGHT); break;
            case Q: snake.setAlive(false); break;
        }
    }

}
