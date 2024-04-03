package com.group25.GUI;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;

import com.group25.User;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main class that launches the application and loads the main scene.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class MainMenu extends Application {

    /**
     * Starts the JavaFX application.
     * 
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {

            //Set icon on the taskbar/dock
            if (Taskbar.isTaskbarSupported()) {
                Taskbar taskbar = Taskbar.getTaskbar();

                if (taskbar.isSupported(Feature.ICON_IMAGE)) {
                    final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                    var dockIcon = defaultToolkit.getImage("src/main/java/com/group25/GUI/assets/icon.png");
                    taskbar.setIconImage(dockIcon);
                }

            }
            List<User> userInfoList = User.getAllPlayersInfo();

            if (userInfoList.isEmpty()) {
                root = FXMLLoader.load(getClass().getResource("fxml/FirstEditUserScene.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("fxml/MainScene.fxml"));
            }
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Math Monster Mayhem!");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);

            Image appIcon = new Image("file:src/main/java/com/group25/GUI/assets/icon.png");

            primaryStage.getIcons().add(appIcon);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method that launches the JavaFX application.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
} 