package com.toxin.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Game extends Application {

    public static final int W = 400;
    public static final int H = 400;

    public static Room room;

    private Canvas canvas;
    private Pane root;
    private Scene scene;

    private Snake snake;

    @Override
    public void start(Stage primaryStage) {
        this.canvas = new Canvas(W, H);
        this.root = new Pane();
        this.scene = new Scene(root, W, H);
        this.snake = new Snake(Room.W / 2, Room.H / 2);

        scene.setOnKeyPressed(new Controller(snake));
        root.getChildren().add(canvas);

        primaryStage.setTitle("Project_SNAKE");
        primaryStage.setScene(scene);
        primaryStage.show();

        room = new Room(canvas, snake);
        new Thread(room).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
