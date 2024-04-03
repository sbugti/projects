package com.group25.GUI.controller;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import com.group25.BubsFancyButtonBorder;
import com.group25.Outfit;
import com.group25.User;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The AdminDashboardController class controls the functionality of the admin dashboard.
 * It handles updating user information and navigation back to the main scene.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah
 */
public class AdminDashboardController {

    @FXML
    private BubsFancyButtonBorder backBtn;

    @FXML
    private Label labelFeedback;

    @FXML
    private TableView<User> leaderboardTable;

    @FXML
    private TableColumn<User, Number> rankColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, Number> scoreColumn;

    @FXML
    private TableColumn<User, Number> levelColumn;

    @FXML
    private ComboBox<Integer> levelSelect;

    @FXML
    private TextField usernameText;

    /**
     * Initializes the admin dashboard.
     * Populates the leaderboard table and initializes UI elements.
     */
    public void initialize() {
        // Populate leaderboard with top 5 highest scores
        List<User> userList = User.getAllPlayersInfo();
    
        // Sort the userList based on username alphabetically
        userList.sort(Comparator.comparing(User::getUsername));
    
        // Clear existing items from the leaderboard table
        leaderboardTable.getItems().clear();
    
        // Populate the leaderboard table with the users
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            // Add user to leaderboard table
            leaderboardTable.getItems().add(user);
        }
    
        // Bind rankColumn with user's rank (index + 1)
        rankColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getTableView().getItems().indexOf(cellData.getValue()) + 1));
    
        // Bind nameColumn with user's name
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUsername()));
    
        // Bind scoreColumn with user's score
        scoreColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getScore()));
    
        // Bind levelColumn with user's level
        levelColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getLevel()));
    
        // Make rows selectable
        leaderboardTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    
        // Add event handler to update usernameText and levelSelect when a row is selected
        leaderboardTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Set the selected username to the usernameText field
                usernameText.setText(newSelection.getUsername());
                // Set the selected user's level in the levelSelect ComboBox
                levelSelect.setValue(newSelection.getLevel());
            } else {
                // If no row is selected, clear the usernameText field and set levelSelect to null
                usernameText.clear();
                levelSelect.setValue(null);
            }
        });

        levelSelect.getItems().clear();
        // Initialize the ComboBox with values from 1 to 5
        for (int i = 1; i <= 5; i++) {
            levelSelect.getItems().add(i);
        }    
    }
    
    /**
     * Handles the event when the update button is clicked.
     * Updates the user's level and saves the changes.
     * Displays feedback to the user.
     *
     * @param event The action event triggered by the update button click.
     */
    @FXML
    private void updateBtnClicked(ActionEvent event) {
        if (usernameText.getText() != null) {
            User currentUser = new User("", 0, 0, new Outfit());
            currentUser.loadUser(usernameText.getText());
            currentUser.setLevel(levelSelect.getValue());
            currentUser.saveUser();
            initialize();
    
            // Show labelFeedback for a second
            labelFeedback.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> labelFeedback.setVisible(false)));
            timeline.play();
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
