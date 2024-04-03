package com.group25.GUI.controller;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.IOException;

import com.group25.BubsFancyInstructionBackButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class for handling instructions UI.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class InstructionController {
    
    @FXML
    private BubsFancyInstructionBackButton btnBack;

    @FXML 
    private Button btnOverview;

    @FXML 
    private Button btnPowerup;
    
    @FXML 
    private Button btnLevelCreator;

    @FXML 
    private Button btnControls;

    @FXML 
    private AnchorPane OverviewPane;

    @FXML 
    private AnchorPane PowerupPane;

    @FXML 
    private AnchorPane LevelCreatorPane;

    @FXML 
    private AnchorPane ControlsPane;

    private String ACTIVE = "-fx-background-color: transparent; -fx-text-fill: #bfac36;";
    private String INACTIVE = "-fx-background-color: transparent; -fx-text-fill: white;";

    /**
     * Handles the click event for the Overview button.
     * @param event The action event.
     */
    @FXML
    void btnOverviewClicked(ActionEvent event) {
        
        if (!OverviewPane.isVisible()){

            loadInstructions(true, false, false, false);
            btnOverview.setStyle(ACTIVE);
            btnPowerup.setStyle(INACTIVE);
            btnLevelCreator.setStyle(INACTIVE);
            btnControls.setStyle(INACTIVE);
        }
    }

    /**
     * Handles the click event for the Powerup button.
     * @param event The action event.
     */
    @FXML
    void btnPowerupClicked(ActionEvent event) {
        
        if (!PowerupPane.isVisible()) {

            loadInstructions(false, true, false, false);
            btnOverview.setStyle(INACTIVE);
            btnPowerup.setStyle(ACTIVE);
            btnLevelCreator.setStyle(INACTIVE);
            btnControls.setStyle(INACTIVE);
        }
    }

    /**
     * Handles the click event for the Level Creator button.
     * @param event The action event.
     */
    @FXML
    void btnLevelCreatorClicked(ActionEvent event) {
        
        if (!LevelCreatorPane.isVisible()) {

            loadInstructions(false, false, true, false);
            btnOverview.setStyle(INACTIVE);
            btnPowerup.setStyle(INACTIVE);
            btnLevelCreator.setStyle(ACTIVE);
            btnControls.setStyle(INACTIVE);
        }
    }

    /**
     * Handles the click event for the Controls button.
     * @param event The action event.
     */
    @FXML
    void btnControlsClicked(ActionEvent event) {
        
        if (!ControlsPane.isVisible()) {

            loadInstructions(false, false, false, true);
            btnOverview.setStyle(INACTIVE);
            btnPowerup.setStyle(INACTIVE);
            btnLevelCreator.setStyle(INACTIVE);
            btnControls.setStyle(ACTIVE);
        }
    }

    /**
     * Loads the corresponding instruction panes based on the selected option.
     * @param overview Indicates if the overview pane should be visible.
     * @param powerup Indicates if the powerup pane should be visible.
     * @param levelCreator Indicates if the level creator pane should be visible.
     * @param controls Indicates if the controls pane should be visible.
     */
    private void loadInstructions(boolean overview, boolean powerup, boolean levelCreator, boolean controls){

        ControlsPane.setVisible(controls);
        PowerupPane.setVisible(powerup);
        LevelCreatorPane.setVisible(levelCreator);
        OverviewPane.setVisible(overview);

    }

    /**
     * Handles the click event for the back button.
     * @param event The action event.
     */
    @FXML
    void handleBackButtonClick(ActionEvent event) {
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
            Stage currentStage = (Stage) btnBack.getScene().getWindow();
            currentStage.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
