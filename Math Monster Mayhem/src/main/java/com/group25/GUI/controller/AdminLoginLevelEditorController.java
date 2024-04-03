package com.group25.GUI.controller;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.IOException;

import com.group25.BubsFancyButtonBorder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The AdminLoginLevelEditorController class controls the functionality of the admin login for level editor.
 * It handles user authentication and navigation to the level maker scene.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah
 */
public class AdminLoginLevelEditorController {

    @FXML
    private BubsFancyButtonBorder backBtn;

    @FXML
    private BubsFancyButtonBorder loginBtn;

    @FXML
    private PasswordField passwordText;

    /**
     * Handles the event when the login button is clicked.
     * Authenticates the user and navigates to the level maker scene if the password is correct.
     *
     * @param event The action event triggered by the login button click.
     */
    @FXML
    private void loginBtnClicked(ActionEvent event) {
        // Handle the login button click event here
        String enteredPassword = passwordText.getText();
        if (enteredPassword.equals("password")) { // Change "password" to your desired password
            // Password is correct, load another scene
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group25/GUI/fxml/LevelMakerScene.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                Image appIcon = new Image("file:src/main/java/com/group25/GUI/assets/icon.png");

                stage.getIcons().add(appIcon);

                stage.show();

                // Close the current stage (Login scene)
                Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Password is incorrect, you may display an error message or take other actions
            System.out.println("Incorrect password. Please try again.");
        }
    }

    /**
     * Handles the event when the back button is clicked.
     * Navigates back to the main scene.
     *
     * @param event The action event triggered by the back button click.
     */
    @FXML
    private void backBtnClicked(ActionEvent event) {
        // Handle the back button click event here
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
            // Load the MainScene.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group25/GUI/fxml/MainScene.fxml"));
            Parent root = loader.load();

            // set scene of cur stage to main
            // currentStage.setTitle("Hello World!");
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            Image appIcon = new Image("file:src/main/java/com/group25/GUI/assets/icon.png");

            stage.getIcons().add(appIcon);

            stage.show();

            // Close the current stage (Instruction scene)
            Stage currentStage = (Stage) backBtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
