package com.group25.GUI.controller;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.group25.BubsFancyButtonBorder;
import com.group25.Outfit;
import com.group25.User;
import com.group25.User.CurrentUser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for handling the editing of character outfit.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah
 */
public class EditCharacterController {
    
    @FXML
    private Label profileText;

    @FXML
    private BubsFancyButtonBorder backBtn;

    @FXML
    private BubsFancyButtonBorder switchProfileBtn;

    @FXML
    private Pane hat1Border, hat2Border, hat3Border, hat4Border;
    
    @FXML
    private Pane skin1Border, skin2Border, skin3Border, skin4Border;
    
    @FXML
    private Pane shirt1Border, shirt2Border, shirt3Border, shirt4Border;

    @FXML
    private Label labelFeedback;

    @FXML
    private ImageView hat1Btn, hat2Btn, hat3Btn, hat4Btn,
            skin1Btn, skin2Btn, skin3Btn, skin4Btn,
            shirt1Btn, shirt2Btn, shirt3Btn, shirt4Btn;
    
    private String currentUsername; // Declaring currentUsername as a class-level variable
    
    private int selectedHat = 0;
    private int selectedSkin = 0;
    private int selectedShirt = 0;

    // Define the border stroke for default and highlighted states
    BorderStroke defaultBorderStroke = new BorderStroke(
        null,                           // No color for default state
        BorderStrokeStyle.NONE,         // No border style for default state
        null,                           // No corner radii for default state
        null                            // No border width for default state
    );

    BorderStroke highlightedBorderStroke = new BorderStroke(
        Color.WHITE,                    // Border color for highlighted state
        BorderStrokeStyle.SOLID,        // Border style for highlighted state
        CornerRadii.EMPTY,              // No corner radii for highlighted state
        new BorderWidths(3)             // Border width for highlighted state
    );
    // Create borders with the defined strokes
    Border defaultBorder = new Border(defaultBorderStroke);
    Border highlightedBorder = new Border(highlightedBorderStroke);
    
    /**
     * Initializes the controller.
     */
    @FXML
    private void initialize() {
        // Load the current user
        currentUsername = null; // Initialize currentUsername
        try {
            currentUsername = CurrentUser.loadFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // If current user is loaded successfully, set their outfit
        if (currentUsername != null) {
            profileText.setText(currentUsername + "'s Profile");
            User currentUser = new User("", 0, 0, new Outfit());
            currentUser.loadUser(currentUsername);
            Outfit outfit = currentUser.getOutfit();
            selectedHat = outfit.getHat();
            selectedSkin = outfit.getFace();
            selectedShirt = outfit.getShirt();
            updateUI();
        }


        // Apply the border to your Pane
        Map<ImageView, Pane> imageViewToPaneMap = new HashMap<>();
        imageViewToPaneMap.put(hat1Btn, hat1Border);
        imageViewToPaneMap.put(hat2Btn, hat2Border);
        imageViewToPaneMap.put(hat3Btn, hat3Border);
        imageViewToPaneMap.put(hat4Btn, hat4Border);
        imageViewToPaneMap.put(skin1Btn, skin1Border);
        imageViewToPaneMap.put(skin2Btn, skin2Border);
        imageViewToPaneMap.put(skin3Btn, skin3Border);
        imageViewToPaneMap.put(skin4Btn, skin4Border);
        imageViewToPaneMap.put(shirt1Btn, shirt1Border);
        imageViewToPaneMap.put(shirt2Btn, shirt2Border);
        imageViewToPaneMap.put(shirt3Btn, shirt3Border);
        imageViewToPaneMap.put(shirt4Btn, shirt4Border);

        for (Map.Entry<ImageView, Pane> entry : imageViewToPaneMap.entrySet()) {
            ImageView imageView = entry.getKey();
            Pane pane = entry.getValue();
            // Add mouse enter event handler to apply highlighted border
            imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    pane.setBorder(highlightedBorder);
                }
            });

            // Add mouse exit event handler to apply default border
            imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    pane.setBorder(defaultBorder);
                }
            });
        }
    }

    /**
     * Handles the selection of a hat.
     *
     * @param hatNumber The index of the selected hat.
     */
    private void selectHat(int hatNumber) {
        selectedHat = hatNumber;
        updateUI();
    }
    
    /**
     * Handles the selection of a skin.
     *
     * @param skinNumber The index of the selected skin.
     */
    private void selectSkin(int skinNumber) {
        selectedSkin = skinNumber;
        updateUI();
    }
    
    /**
     * Handles the selection of a shirt.
     *
     * @param skinNumber The index of the selected skin.
     */
    private void selectShirt(int shirtNumber) {
        selectedShirt = shirtNumber;
        updateUI();
    }
    
    /**
     * Updates the UI based on the selected values.
     */
    private void updateUI() {
        
        // Update hat borders
        hat1Border.setStyle(selectedHat == 0 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        hat2Border.setStyle(selectedHat == 1 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        hat3Border.setStyle(selectedHat == 2 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        hat4Border.setStyle(selectedHat == 3 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        
        // Update skin borders
        skin1Border.setStyle(selectedSkin == 0 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        skin2Border.setStyle(selectedSkin == 1 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        skin3Border.setStyle(selectedSkin == 2 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        skin4Border.setStyle(selectedSkin == 3 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");

        // Update shirt borders
        shirt1Border.setStyle(selectedShirt == 0 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        shirt2Border.setStyle(selectedShirt == 1 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        shirt3Border.setStyle(selectedShirt == 2 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
        shirt4Border.setStyle(selectedShirt == 3 ? "-fx-background-color: blue;" : "-fx-background-color: gray;");
    }
    
    // Event handlers for hat buttons
    @FXML
    private void hat1BtnClicked() {
        selectHat(0);
    }
    
    @FXML
    private void hat2BtnClicked() {
        selectHat(1);
    }
    
    @FXML
    private void hat3BtnClicked() {
        selectHat(2);
    }
    
    @FXML
    private void hat4BtnClicked() {
        selectHat(3);
    }
    
    // Event handlers for skin buttons
    @FXML
    private void skin1BtnClicked() {
        selectSkin(0);
    }
    
    @FXML
    private void skin2BtnClicked() {
        selectSkin(1);
    }
    
    @FXML
    private void skin3BtnClicked() {
        selectSkin(2);
    }

    @FXML
    private void skin4BtnClicked() {
        selectSkin(3);
    }
    
    // Event handlers for shirt buttons
    @FXML
    private void shirt1BtnClicked() {
        selectShirt(0);
    }
    
    @FXML
    private void shirt2BtnClicked() {
        selectShirt(1);
    }
    
    @FXML
    private void shirt3BtnClicked() {
        selectShirt(2);
    }
    
    @FXML
    private void shirt4BtnClicked() {
        selectShirt(3);
    }
    
    /**
     * Handles the click event for the save button.
     *
     * @param event The action event.
     */
    @FXML
    private void saveBtnClicked(ActionEvent event) {
        // Update the outfit with selected values
        Outfit newOutfit = new Outfit();
        newOutfit.updateOutfit(new int[]{selectedHat, selectedSkin, selectedShirt});
        // If current user is loaded successfully, set their outfit
        if (currentUsername != null) {
            User currentUser = new User("", 0, 0, new Outfit());
            currentUser.loadUser(currentUsername);
            currentUser.setOutfit(newOutfit);
            currentUser.saveUser();
            updateUI();

            // Show labelFeedback for a second
            labelFeedback.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> labelFeedback.setVisible(false)));
            timeline.play();
        }
    }
    
    /**
     * Handles the click event for the back button.
     *
     * @param event The action event.
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
    
    /**
     * Handles the click event for the switch profile button.
     *
     * @param event The action event.
     */
    @FXML
    private void switchProfileBtnClicked(ActionEvent event) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group25/GUI/fxml/EditUserScene.fxml"));
            Parent root = loader.load();

            
            // set scene of cur stage to main
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            
            // Close the current stage (Instruction scene)
            Stage currentStage = (Stage) switchProfileBtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
