package com.group25.GUI.controller;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group25.BubsFancyButtonBorder;
import com.group25.Outfit;
import com.group25.User;
import com.group25.User.CurrentUser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for the Edit User scene.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class EditUserController {

    @FXML
    private BubsFancyButtonBorder btnBack;

    @FXML
    private ComboBox<String> userList;

    @FXML
    private Label labelFeedback1;

    @FXML
    private TextField usernameText;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     */
    public void initialize() { 
        updateInfo();
    }

    /**
     * Updates user information in the ComboBox.
     */
    public void updateInfo() {
        // Get all player information from JSON files
        List<User> userInfoList = User.getAllPlayersInfo();
        
        // Extract usernames from user information
        ObservableList<String> usernames = FXCollections.observableArrayList();
        for (User user : userInfoList) {
            usernames.add(user.getUsername());
        }
    
        if (usernames.size() == 0) {
            btnBack.setDisable(true);
        }

        // Populate ComboBox with usernames
        userList.setItems(usernames);
        
        // Set the default value of ComboBox to the current user found in current_user.json
        String currentUser;
        try {
            currentUser = CurrentUser.loadFromFile();
        } catch (IOException exception) {
            // Show labelFeedback for a second
            labelFeedback1.setText("Create Your First User!");
            labelFeedback1.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> labelFeedback1.setVisible(false)));
            timeline.play();
            return; // Exit the method if an exception occurs
        }
        if (currentUser != null) {
            userList.setValue(currentUser);
        }
    }
    
    /**
     * Handles the action when the choose profile button is clicked.
     * It updates the current user in the application and provides feedback to the user.
     * 
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void chooseProfileBtnClicked(ActionEvent event) {
        // Get the selected username from the ComboBox
        String selectedUsername = userList.getValue();
        if (selectedUsername != null && !selectedUsername.isEmpty()) {
            try {
                // Update the current user in current_user.json
                String updatedCurrentUser = CurrentUser.saveToFile(selectedUsername);
                // Set the ComboBox value to the updated current user
                userList.setValue(updatedCurrentUser);
                System.out.println("test");
                // Show labelFeedback for a second
                labelFeedback1.setText("Profile Switched!");
                labelFeedback1.setVisible(true);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> labelFeedback1.setVisible(false)));
                timeline.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    /**
     * Handles the action when the create button is clicked.
     * It creates a new user profile with the entered username if it doesn't already exist,
     * and updates the UI accordingly.
     * 
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void createBtnClicked(ActionEvent event) {
        
        // Retrieve the username entered in the TextField
        String newUsername = usernameText.getText().trim();
        ObjectMapper mapper = new ObjectMapper();

        // Check if the username is not empty
        if (!newUsername.isEmpty()) {
            try {
                // Check if user already exists
                User temp = mapper.readValue(new File(newUsername + ".json"), User.class);
                // Show labelFeedback for a second
                labelFeedback1.setText("User Already Exists!");
                labelFeedback1.setVisible(true);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> labelFeedback1.setVisible(false)));
                timeline.play();
            } catch (Exception exception) {
                // Create a new user with default values
                Outfit defaultOutfit = new Outfit();
                User newUser = new User(newUsername, 1, 0, defaultOutfit);
                
                // Save the new user
                newUser.saveUser();
                btnBack.setDisable(false);

                usernameText.setText("");

                // Update the list of users in the ComboBox
                updateInfo();

                // Show labelFeedback for a second
                labelFeedback1.setText("New Profile Created and Switched to!");
                labelFeedback1.setVisible(true);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> labelFeedback1.setVisible(false)));
                timeline.play();
            }
        } else {
            labelFeedback1.setText("Please Enter A Valid Username!");
            labelFeedback1.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> labelFeedback1.setVisible(false)));
            timeline.play();
        }
        
    }

    /**
     * Handles the action when the back button is clicked.
     * It loads the main scene and closes the current window.
     * 
     * @param event The ActionEvent triggered by the button click.
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group25/GUI/fxml/EditCharacterScene.fxml"));
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
            Stage currentStage = (Stage) btnBack.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
