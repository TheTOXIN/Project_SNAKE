package com.toxin.snake;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Основной класс программы.
 */
public class Room implements Runnable {

    public static final int W = 20;
    public static final int H = 20;
    public static final int S = 20;

    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;
    private Canvas canvas;

    public Room(Canvas canvas, Snake snake) {
        this.canvas = canvas;
        this.width = W;
        this.height = H;
        this.snake = snake;

        getSnake().setDirection(SnakeDirection.DOWN);
        createMouse();
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    /**
     * Основной цикл программы.
     * Тут происходят все важные действия
     */
    @Override
    public void run() {
        //пока змея жива
        while (snake.isAlive()) {
            snake.move();   //двигаем змею
            render();        //отображаем текущее состояние игры
            print();
            sleep();        //пауза между ходами
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }

    /**
     * Выводим на экран текущее состояние игры
     */
    public void print() {
        int[][] matrix = makeMatrix();

        //Выводим все это на экран
        String[] symbols = {" . ", " x ", " O ", "^_^", "RIP"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    private void render() {
        int[][] matrix = makeMatrix();

        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, Game.W, Game.H);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (matrix[y][x]) {
                    case 0: /*skip*/ break;
                    case 1: renderSnakeBody(x, y, Color.GREEN, graphics);  break;
                    case 2: renderSnakeHead(x, y, Color.GREEN, graphics); break;
                    case 3: renderMouse(x, y, Color.RED, graphics);break;
                    case 4: renderSnakeHead(x, y, Color.WHITE, graphics);break;
                }
            }
        }
    }

    private void renderSnakeBody(int x, int y, Color color, GraphicsContext graphics) {
        graphics.setFill(color);
        graphics.fillRoundRect(x * S, y * S, S, S, S / 2, S / 2);
    }

    private void renderSnakeHead(int x, int y, Color color, GraphicsContext graphics) {
        graphics.setFill(color);
        graphics.fillRect(x * S, y * S, S, S);
    }

    private void renderMouse(int x, int y, Color color, GraphicsContext graphics) {
        graphics.setFill(color);
        graphics.fillOval(x * S, y * S, S, S);
    }

    private int[][] makeMatrix() {
        //Создаем массив, куда будем "рисовать" текущее состояние игры
        int[][] matrix = new int[height][width];

        //Рисуем все кусочки змеи
        ArrayList<SnakeSection> sections = new ArrayList<>(snake.getSections());
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }

        //Рисуем голову змеи (4 - если змея мертвая)
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;

        //Рисуем мышь
        matrix[mouse.getY()][mouse.getX()] = 3;

        return matrix;
    }

    /**
     * Метод вызывается, когда мышь съели
     */
    public void eatMouse() {
        createMouse();
    }

    /**
     * Создает новую мышь
     */
    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        mouse = new Mouse(x, y);
    }

    //Массив "пауз" в зависимости от уровня.
    private static int[] levelDelay = {1000, 600, 550, 500, 480, 460, 440, 420, 400, 380, 360, 340, 320, 300, 285, 270};

    /**
     * Прогрмма делает паузу, длинна которой зависит от длинны змеи.
     */
    public void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level < 15 ? levelDelay[level] : 250;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
