package com.group25;

// package sandboxfx;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A simple JavaFX application demonstrating sprite animation.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class SandboxFX extends Application {

    private static final Image IMAGE = new Image("http://upload.wikimedia.org/wikipedia/commons/7/73/The_Horse_in_Motion.jpg");

    private static final int COLUMNS  =   4;
    private static final int COUNT    =  10;
    private static final int OFFSET_X =  18;
    private static final int OFFSET_Y =  25;
    private static final int WIDTH    = 374;
    private static final int HEIGHT   = 243;

    /**
     * The entry point of the application.
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and starts the JavaFX application.
     * @param primaryStage The primary stage of the application.
     */
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");

        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        primaryStage.setScene(new Scene(new Group(imageView)));
        primaryStage.show();
    }
}
