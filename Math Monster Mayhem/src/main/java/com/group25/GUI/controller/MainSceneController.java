package com.group25.GUI.controller;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import com.group25.AudioPlayer;
import com.group25.AudioPlayer.SoundEffect;
import com.group25.BubsFancyButton;
import com.group25.Outfit;
import com.group25.User;
import com.group25.User.CurrentUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controller class for the main scene.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class MainSceneController {

    @FXML
    private BubsFancyButton btnPlay;
    
    @FXML
    private BubsFancyButton btnNewGame;

    @FXML
    private BubsFancyButton btnLoadGame;

    @FXML
    private BubsFancyButton btnChallengeGame;

    @FXML
    private BubsFancyButton btnInstructions;

    @FXML
    private BubsFancyButton btnLevelMaker;

    @FXML 
    private BubsFancyButton btnLeaderboard;

    @FXML
    private BubsFancyButton btnEditUser;
    
    @FXML
    private BubsFancyButton btnExit;
    
    @FXML
    private BubsFancyButton btnAdmin;

    @FXML
    private Label profileText;

    @FXML
    private Label levelText;

    @FXML
    private Label scoreText;

    @FXML
    private ImageView shirtImg;

    @FXML
    private ImageView skinImg;

    @FXML
    private ImageView hatImg;


    // Additional fields
    int backLevel = 0;
    boolean menuExpand = false;
    public static AudioPlayer backgroundMusic = new AudioPlayer(true);
    private String currentUsername;

    /**
     * Initializes the controller class.
     * Sets up the initial state of the main scene.
     */
    @FXML
    void initialize(){
        // Stop any existing background music and start the main menu music
        backgroundMusic.stop();
        backgroundMusic.play(SoundEffect.MenuMusic);

        try {
            // Attempt to load the current user from file
            currentUsername = CurrentUser.loadFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // If current user is loaded successfully, set their outfit, level, and highscore
        if (currentUsername != null) {
            User currentUser = new User("", 0, 0, new Outfit());
            currentUser.loadUser(currentUsername);
            profileText.setText(currentUser.getUsername() + "'s Profile");
            levelText.setText("Level: " + currentUser.getLevel());
            scoreText.setText("High Score: " + currentUser.getScore());
            hatImg.setImage(new Image(new File("src/main/java/com/group25/GUI/assets/visuals/character/hat" + (currentUser.getOutfit().getHat() + 1) + ".png").toURI().toString()));
            skinImg.setImage(new Image(new File("src/main/java/com/group25/GUI/assets/visuals/character/skin" + (currentUser.getOutfit().getFace() + 1) + ".png").toURI().toString()));
            shirtImg.setImage(new Image(new File("src/main/java/com/group25/GUI/assets/visuals/character/shirt" + (currentUser.getOutfit().getShirt() + 1) + ".png").toURI().toString()));
        }

    }

    /**
     * Loads a new scene based on the provided scene name.
     *
     * @param sceneName the name of the FXML file representing the scene to be loaded
     */
    void loadScene(String sceneName){
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
            // Load the the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group25/GUI/fxml/" + sceneName + ".fxml"));
            Parent root = loader.load();
            
            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            Image appIcon = new Image("file:src/main/java/com/group25/GUI/assets/icon.png");
            stage.getIcons().add(appIcon);
            stage.show();

            // Stop the background music and close the current stage (main scene)
            backgroundMusic.stop();
            Stage currentStage = (Stage) btnPlay.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler method for when the play button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnPlayClicked(ActionEvent event) throws IOException {
        menuExpand = !menuExpand;
        if (menuExpand) {
            // Enable and make visible the buttons
            btnNewGame.setDisable(false);
            btnNewGame.setVisible(true);
            btnLoadGame.setDisable(false);
            btnLoadGame.setVisible(true);
            btnChallengeGame.setDisable(false);
            btnChallengeGame.setVisible(true);
            backLevel = 1;
        }
        else {
            // Disable and hide the buttons
            btnNewGame.setDisable(true);
            btnNewGame.setVisible(false);
            btnLoadGame.setDisable(true);
            btnLoadGame.setVisible(false);
            btnChallengeGame.setDisable(true);
            btnChallengeGame.setVisible(false);
            backLevel = 1;
        }
    }
 

    /**
     * Event handler method for when the new game button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnNewGameClicked(ActionEvent event) {        
        // If current user is loaded successfully, reset their level and start a new game
        if (currentUsername != null) {
            User currentUser = new User("", 0, 0, new Outfit());
            currentUser.loadUser(currentUsername);
            currentUser.setLevel(1);
            currentUser.saveUser();
        }
        // Load Game Scene From Beginning and stop music
        loadScene("GameScene");
        backgroundMusic.stop();
    }

    /**
     * Event handler method for when the load game button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnLoadGameClicked(ActionEvent event) {
        // Load Game Scene From Save and stop music
        loadScene("GameScene");
        backgroundMusic.stop();
    }

    /**
     * Event handler method for when the challenge mode button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnChallengeGameClicked(ActionEvent event) {
        // Load Challenge Game Scene and stop music
        loadScene("ChallengeGameScene");
        backgroundMusic.stop();
    }

    /**
     * Event handler method for when the instructions button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnInstructionsClicked(ActionEvent event) {
        // Load Instructions Scene
        loadScene("InstructionScene");

        //start the audio player
        backgroundMusic.stop();
        backgroundMusic.play(AudioPlayer.SoundEffect.Fluffingaduck);
    }

    /**
     * Event handler method for when the leaderboard button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnLeaderboardClicked(ActionEvent event) {
        // Load Leaderboard Scene and stop music
        loadScene("LeaderboardScene");
        backgroundMusic.stop();
    }

    /**
     * Event handler method for when the level maker button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */    
    @FXML
    void btnLevelMakerClicked(ActionEvent event) {
        // Load Level Maker Scene and stop music
        loadScene("AdminLoginLevelEditorScene");
        backgroundMusic.stop();
    }

    /**
     * Event handler method for when the edit user button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */   
    @FXML
    void btnEditUserClicked(ActionEvent event) {
        // Load Edit Character Scene and stop music
        loadScene("EditCharacterScene");
        backgroundMusic.stop();
    }

    /**
     * Event handler method for when the dashboard button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */   
    @FXML
    void btnAdminClicked(ActionEvent event) {
        // Load Admin Login and stop music
        loadScene("AdminLoginDashboardScene");
        backgroundMusic.stop();
    }

    /**
     * Event handler method for when the exit button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnExitClicked(ActionEvent event) {
        // Alert asking for exit confirmation
        Alert alert = new Alert(AlertType.WARNING, "Do you want to close the game?", ButtonType.YES, ButtonType.CANCEL);
    	alert.setHeaderText("");
		alert.showAndWait();
        

		if (alert.getResult() == ButtonType.YES) {
			// Platform.exit();
            backgroundMusic.stop();
            System.exit(0);
		}
    }
    
}
