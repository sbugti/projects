package com.group25.GUI.controller;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import com.group25.Answer;
import com.group25.AudioPlayer;
import com.group25.AudioPlayer.SoundEffect;
import com.group25.Outfit;
import com.group25.Question;
import com.group25.QuestionSet;
import com.group25.SpriteAnimation;
import com.group25.User;
import com.group25.User.CurrentUser;
import com.group25.Utils;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for the game scene.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class GameSceneController implements Initializable {

    @FXML
    private Label powerups;

    @FXML
    private Label timeText;
    private ScaleTransition scaleTransition;

    @FXML
    private Label levelText;

    @FXML
    private Label questionText;

    @FXML
    private Button btnChoiceA;

    @FXML
    private AnchorPane startPane;

    @FXML
    private Button btnChoiceB;

    @FXML
    private Button btnChoiceC;

    @FXML
    private Button btnChoiceD;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnStart;

    @FXML
    private AnchorPane pausePane;

    @FXML
    private Button btnContinue;

    @FXML
    private Button btnPauseBack;

    @FXML
    private AnchorPane winPane;

    @FXML
    private Label winLevelText;

    @FXML
    private Button btnWinBack;

    @FXML
    private AnchorPane losePane;

    @FXML
    private Label loseLevelText;

    @FXML
    private Button btnLoseBack;

    @FXML
    private ProgressBar playerHealth;

    @FXML
    private Label playerHealthText;

    @FXML
    private ProgressBar enemyHealth;

    @FXML
    private Label enemyHealthText;

    @FXML
    private ImageView hatImg;

    @FXML
    private ImageView skinImg;

    @FXML
    private ImageView shirtImg;

    @FXML 
    private ImageView enemyImg;

    @FXML
    private ImageView shadowImg;
    @FXML
    private ImageView confettiGIF;
    
    @FXML
    private ImageView slashGIF;

    @FXML
    private Text bustPane;

    @FXML
    private Label learnLevelNumText;

    @FXML
    private Button btnTryAgain;
    
    private Stack<Question> questionStack;
    private String currentUsername;
    private User currentUser;
    private Question currentQuestion;
    private Answer[] answers;
    private AudioPlayer soundFX;

    private int timeRemaining = 10; // Initial time remaining
    private Timeline timeline = null;
    private int questionsAnsweredCorrectly = 0;
    private int index = 0;
    boolean isPaused;
    boolean isPlaying = true;

    private String visualPath = "src/main/java/com/group25/GUI/assets/visuals/";


    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root object, or {@code null} if the location is not known
     * @param resourceBundle the resources used to localize the root object, or {@code null} if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startPane.setDisable(false);
        btnStart.setDisable(false);
        startPane.setVisible(true);
        btnStart.setVisible(true);
        btnChoiceA.setDisable(true);
        btnChoiceB.setDisable(true);
        btnChoiceC.setDisable(true);
        btnChoiceD.setDisable(true);

        playerHealth.setProgress(1.0);
        playerHealth.setProgress(1.0);
        enemyHealth.setProgress(1.0);
        enemyHealth.setProgress(1.0);
        playerHealthText.setText(String.valueOf((int)(playerHealth.getProgress() * 100)) + " / 100");
        enemyHealthText.setText(String.valueOf((int)(enemyHealth.getProgress() * 100)) + " / 100");

        Button[] buttons = {btnChoiceA, btnChoiceB, btnChoiceC, btnChoiceD};
        for (Button button: buttons) {
            button.setStyle(Utils.defaultStyle); // Set default style);
            button.setOnMouseEntered(e -> button.setStyle(Utils.hoverStyle)); // Add hover effect
            button.setOnMouseExited(e -> button.setStyle(Utils.defaultStyle)); // Restore default style
        }

        // Initialize ScaleTransition
        scaleTransition = new ScaleTransition(Duration.seconds(0.1), timeText);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setOnFinished(event -> {
            timeText.setScaleX(1.0);
            timeText.setScaleY(1.0);
        });

        try {
            currentUsername = CurrentUser.loadFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentUser = new User(currentUsername, timeRemaining, null);
        currentUser.loadUser(currentUsername);
        
        learnLevelNumText.setText(String.valueOf(currentUser.getLevel()));


        slashGIF.setImage(null);
        confettiGIF.setImage(null);
        setupConfetti(true);
    }

    void setupConfetti(boolean isVisible) {
        // Load the spritesheet image
        String path = "src/main/java/com/group25/GUI/assets/visuals/Confetti.png";
        setImageFromPath(confettiGIF, path);
        confettiGIF.setVisible(isVisible);
        SpriteAnimation spriteAnimation = new SpriteAnimation(
                confettiGIF,
                Duration.millis(1000),
                64,
                8,
                0,
                0,
                512,
                512
        );

        // Set cycle count and play animation
        spriteAnimation.setCycleCount(1);
        spriteAnimation.play();
    }

    void setupSlash() {
        // Load the spritesheet image
        String path = "src/main/java/com/group25/GUI/assets/visuals/slash_spritesheet.png";
        setImageFromPath(slashGIF, path);

        SpriteAnimation spriteAnimation = new SpriteAnimation(
                slashGIF,
                Duration.millis(1000),
                16,
                3,
                0,
                0,
                32,
                32
        );

        // Set cycle count and play animation
        spriteAnimation.setCycleCount(1);
        spriteAnimation.play();
    }

    // Method to update timer text
    public void updateTimer(int time) {
        // timeText.setText(Integer.toString(time));
        scaleTransition.playFromStart(); // Play the animation

        if (timeRemaining <= 5) {
            timeText.setTextFill(Color.RED);
        } else {
            timeText.setTextFill(Color.WHITE);
        }
    }

    // Start the timer
    public void startTimer() {
        timeline.play();
    }

    // Stop the timer
    public void stopTimer() {
        timeline.stop();
    }

    public void loadQuestion(){
        this.timeline.stop();
        timeRemaining = 10;
        timeText.setText(Integer.toString(timeRemaining));
        timeText.setTextFill(Color.WHITE);
        this.timeline.play(); // Start the timer

        if (questionStack.isEmpty()) {

            System.out.println(questionsAnsweredCorrectly);
            System.out.println(currentUser.getLevel());
                //TODO add level failed screen
                loseScreen();
                loseLevelText.setText(String.valueOf(currentUser.getLevel()));
                initialize(null, null); //to be replaced later on
                QuestionSet set = new QuestionSet("level" + currentUser.getLevel());
                this.questionStack = set.getListAsStack();
        }
        currentQuestion = questionStack.pop();
            
        questionText.setText(currentQuestion.getQuestion());
        answers = currentQuestion.getAnswers();
        btnChoiceA.setText("1)   " + answers[0].getText());
        btnChoiceB.setText("2)   " + answers[1].getText());
        btnChoiceC.setText("3)   " + answers[2].getText());
        btnChoiceD.setText("4)   " + answers[3].getText());
        
    }

    public void vibrate(Node f, int shakeLength, int shakeIntensity) {

        try {
            //new Thread(() -> {
                final int originalX = (int) ((ImageView) f).getX(); // Get initial position

                TranslateTransition shake = new TranslateTransition(Duration.millis(shakeLength), f);
                shake.setFromX(originalX);
                shake.setByX(shakeIntensity);
                shake.setCycleCount(4); // Shake in each direction twice
                shake.setAutoReverse(true);

                shake.play();

            //}){{start();}}.join();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isCorrect(char btnClicked){

        if (btnClicked == 'a') {
            if (answers[0].isCorrect()) {
                questionsAnsweredCorrectly++;
                return true;
            }
        }
        else if (btnClicked == 'b') {
            if (answers[1].isCorrect()) {
                questionsAnsweredCorrectly++;
                return true;
            }
        }
        else if (btnClicked == 'c') {
            if (answers[2].isCorrect()) {
                questionsAnsweredCorrectly++;
                return true;
            }
        }
        else if (btnClicked == 'd') {
            if (answers[3].isCorrect()) {
                questionsAnsweredCorrectly++;
                return true;
            }
        }
        return false;
    } 

    void loseScreen() {
        isPlaying = false;
        timeline.stop();
        this.soundFX.play(SoundEffect.LevelDefeat);
        losePane.setDisable(false);
        losePane.setVisible(true);
        btnLoseBack.setDisable(false);
        btnLoseBack.setVisible(true);
        btnTryAgain.setDisable(false);
        btnTryAgain.setVisible(true);

        this.currentUser.saveUser();
    }

    void winScreen() {
        isPlaying = false;
        timeline.stop();
        this.soundFX.play(SoundEffect.FinalVictory);
        winPane.setDisable(false);
        winPane.setVisible(true);
        btnWinBack.setDisable(false);
        btnWinBack.setVisible(true);

        this.currentUser.saveUser();
    }
    void playMonsterNoise(boolean death){
        
        try {
            new Thread(()-> {        
                
                if (currentUser.getLevel() == 1) {

                    if (death) {  
                        
                        this.soundFX.play(SoundEffect.SwordDeath);
                    }
                    else{
                        this.soundFX.play(SoundEffect.Sword);
                    }
                }
                else if (currentUser.getLevel() == 2) {

                    if (death) {  
                        this.soundFX.play(SoundEffect.GhostDeath);
                    }
                    else{
                        this.soundFX.play(SoundEffect.Ghost);
                    }
                }
                else if (currentUser.getLevel() == 3) {

                    if (death) {  
                        this.soundFX.play(SoundEffect.GenieDeath);
                    }
                    else{
                        this.soundFX.play(SoundEffect.Genie);
                    }
                }
                else if (currentUser.getLevel() == 4) {

                    if (death) {  
                        this.soundFX.play(SoundEffect.DragonDeath);
                    }
                    else{
                        this.soundFX.play(SoundEffect.Dragon);
                    }
                }
                else{
                    if (death) {  
                        this.soundFX.play(SoundEffect.LambDeath);
                    }
                    else{
                        this.soundFX.play(SoundEffect.Lamb);
                    }
                }
            }){{start();}}.join();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void correctChoiceMade() {
        this.soundFX.play(SoundEffect.CorrectAnswer);
        enemyHealth.setProgress(enemyHealth.getProgress() - 1.0/7.0);
        enemyHealthText.setText(String.valueOf((int)(Math.round(enemyHealth.getProgress() * 1000.0) / 10.0)) + " / 100");
        setupSlash();
        playMonsterNoise(false);
        vibrate(enemyImg, 125, 25);
        vibrate(shadowImg, 125, 25);
        if (Math.round(enemyHealth.getProgress() * 100.0) / 100.0 <= 0) {
            setRandomEnemyImage();
            // confettiGIF.setImage(null);
            setupConfetti(true);
            //this.soundFX.play(SoundEffect.LevelComplete);
            playMonsterNoise(true);
            currentUser.setLevel(currentUser.getLevel()+1);
            if (currentUser.getLevel() > 5) {
                currentUser.setLevel(5);
                winScreen();
                winLevelText.setText(String.valueOf(currentUser.getLevel()));
            }
            currentUser.saveUser();
            bustPane.setText("Congratulations");
            learnLevelNumText.setText(String.valueOf(currentUser.getLevel()));
            isPaused = true;
            timeline.stop();
            timeRemaining = 10;
            initialize(null, null); //to be replaced later on
            QuestionSet set = new QuestionSet("level" + currentUser.getLevel());
            this.questionStack = set.getListAsStack();
            questionsAnsweredCorrectly = 0;
        }
    }

    void incorrectChoiceMade() {
        this.soundFX.play(SoundEffect.IncorrectAnswer);
        playerHealth.setProgress(playerHealth.getProgress() - 1.0/3.0);
        playerHealthText.setText(String.valueOf((int)(Math.round(playerHealth.getProgress() * 1000.0) / 10.0)) + " / 100");

        this.soundFX.play(SoundEffect.Person);
        vibrate(shirtImg, 125, 25);
        vibrate(skinImg, 125, 25);
        vibrate(hatImg, 125, 25);
        
        if (Math.round(playerHealth.getProgress() * 100.0) / 100.0 <= 0) {
            loseScreen();
            loseLevelText.setText(String.valueOf(currentUser.getLevel()));
        }
    }

    private void setRandomEnemyImage() {
        String basePath = visualPath + "monsters/";
        index = currentUser.getLevel(); // Ensure new enemy is different
        String enemyPath = basePath + "enemy" + index + ".png";
        setImageFromPath(enemyImg, enemyPath);
        enemyHealth.setProgress(1.0);
        enemyHealthText.setText(String.valueOf((int)(enemyHealth.getProgress() * 100)) + " / 100");
    }

    /**
     * Event handler method for when Choice A button is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnChoiceAClicked(ActionEvent event) {
        
        if (isCorrect('a')) {
            correctChoiceMade();
        }
        else{
            incorrectChoiceMade();
        }
        loadQuestion();
    }

    @FXML
    void btnChoiceBClicked(ActionEvent event) {
        if (isPaused) return;

        if (isCorrect('b')) {
            correctChoiceMade();
        }
        else{
            if (isPaused) return;
            incorrectChoiceMade();
        }
        loadQuestion();

    }

    @FXML
    void btnChoiceCClicked(ActionEvent event) {
        if (isPaused) return;

        if (isCorrect('c')) {
            correctChoiceMade();
        }
        else{
            if (isPaused) return;
            incorrectChoiceMade();
        }
        loadQuestion();
    }

    @FXML
    void btnChoiceDClicked(ActionEvent event) {
        if (isPaused) return;

        if (isCorrect('d')) {
            correctChoiceMade();
        }
        else{
            if (isPaused) return;
            incorrectChoiceMade();
        }
        loadQuestion();

    }

    @FXML
    void btnPauseClicked(ActionEvent event) {
        isPlaying = false;
        isPaused = true;
        pausePane.setDisable(false);
        pausePane.setVisible(true);
        btnContinue.setDisable(false);
        btnContinue.setVisible(true);
        btnPause.setDisable(true);
        btnPause.setVisible(false);
        btnPauseBack.setDisable(false);
        btnPauseBack.setVisible(true);

        timeline.stop();
    }


    @FXML
    void btnContinueClicked(ActionEvent event) {
        isPaused = false;
        isPlaying = true;

        timeline.play();

        pausePane.setDisable(true);
        pausePane.setVisible(false);
        btnContinue.setDisable(true);
        btnContinue.setVisible(false);
        btnPause.setDisable(false);
        btnPause.setVisible(true);
        btnPauseBack.setDisable(true);
        btnPauseBack.setVisible(false);
        
    }

    @FXML
    void btnStartClicked(ActionEvent event) {
        playerHealth.setProgress(1.0);
        enemyHealth.setProgress(1.0);
        QuestionSet set = new QuestionSet("level" + currentUser.getLevel());
        this.questionStack = set.getListAsStack();
        this.questionStack.size();
        levelText.setText("Level: " + Integer.toString(currentUser.getLevel()));
        btnStart.setVisible(false);
        btnStart.setDisable(true);
        startPane.setVisible(false);
        startPane.setDisable(true);

        isPaused = false;
        playerHealthText.setText(String.valueOf((int)(playerHealth.getProgress() * 100)) + " / 100");

        btnChoiceA.setDisable(false);
        btnChoiceB.setDisable(false);
        btnChoiceC.setDisable(false);
        btnChoiceD.setDisable(false);
        soundFX = new AudioPlayer(false);
        
        setOutfit();
        setRandomEnemyImage();

        if (this.timeline == null) {
            this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), timelineEvent -> {
                timeRemaining--;
                if (timeRemaining < 0) {
                    // Handle timeout
                    playerHealth.setProgress(playerHealth.getProgress() - 0.1);
                    playerHealthText.setText(String.valueOf(Math.round((int)(playerHealth.getProgress() * 1000.0) / 10.0)) + " / 100");

                    if (isPlaying){
                        loadQuestion();
                     } // Stop the timer
                    // Add your logic for timeout here
                }
                else{
                    timeText.setText(Integer.toString(timeRemaining));
                    updateTimer(timeRemaining);

                    if (Math.round(playerHealth.getProgress() * 100.0) / 100.0 <= 0) {
                        //Platform.runLater(()->loseScreen());
                        //Platform.runLater(()->loseLevelText.setText(String.valueOf(currentUser.getLevel())));
                    }
                }
            }));

            this.timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        }
        else{
            timeline.stop();
            timeRemaining = 10;
        }

        loadQuestion();
    }

    public void setOutfit() {
        Outfit userOutfit = this.currentUser.getOutfit();
        String basePath = visualPath + "character/";
    
        // Set hat image based on user's outfit
        String hatPath = basePath + "hat" + (userOutfit.getHat() + 1) + ".png";
        setImageFromPath(hatImg, hatPath);
    
        // Set skin image based on user's outfit
        String skinPath = basePath + "skin" + (userOutfit.getFace() + 1) + ".png";
        setImageFromPath(skinImg, skinPath);
    
        // Set shirt image based on user's outfit
        String shirtPath = basePath + "shirt" + (userOutfit.getShirt() + 1) + ".png";
        setImageFromPath(shirtImg, shirtPath);
    }
    
    private void setImageFromPath(ImageView imageView, String path) {
        File file = new File(path);
        imageView.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    void handleKeyPress(KeyEvent event){

        KeyCode pressedCode = event.getCode();
        if (Utils.isAdmin && pressedCode == KeyCode.Q) {
            correctChoiceMade();
            loadQuestion();
            return;
        }
        if (pressedCode == KeyCode.DIGIT1 || pressedCode == KeyCode.DIGIT2 || pressedCode == KeyCode.DIGIT3 || pressedCode == KeyCode.DIGIT4) {
                
                char btnClicked = 't';

                if(pressedCode == KeyCode.DIGIT1){
                    btnClicked = 'a';
                }
                else if(pressedCode == KeyCode.DIGIT2){
                    btnClicked = 'b';
                }
                else if(pressedCode == KeyCode.DIGIT3){
                    btnClicked = 'c';
                }
                else if(pressedCode == KeyCode.DIGIT4){
                    btnClicked = 'd';
                }
                if (isCorrect(btnClicked)) {
                    correctChoiceMade();
                }
                else {
                    incorrectChoiceMade();
                }
            loadQuestion();
        }
    }

    @FXML
    void btnTryAgainClicked(ActionEvent event) {
        playerHealth.setProgress(1.0);
        enemyHealth.setProgress(1.0);
        QuestionSet set = new QuestionSet("level" + currentUser.getLevel());
        this.questionStack = set.getListAsStack();
        this.questionStack.size();
        levelText.setText("Level: " + Integer.toString(currentUser.getLevel()));
        
        btnTryAgain.setDisable(false);
        btnTryAgain.setVisible(true);
        btnLoseBack.setVisible(false);
        btnLoseBack.setDisable(true);
        losePane.setVisible(false);
        losePane.setDisable(true);

        isPaused = false;
        playerHealthText.setText(String.valueOf((int)(playerHealth.getProgress() * 100)) + " / 100");

        btnChoiceA.setDisable(false);
        btnChoiceB.setDisable(false);
        btnChoiceC.setDisable(false);
        btnChoiceD.setDisable(false);
        soundFX = new AudioPlayer(false);

    }

    @FXML
    void btnMenuClicked(ActionEvent event) {
        //TODO -- save state??
        /*
         * Pseudocode for save state
         * 
         * GET the current level
         * SET user level to current level
         * SAVE user object to json
         * 
         */
        timeline.stop();
        soundFX.stop();
        this.soundFX.play(SoundEffect.Quit);
        this.currentUser.saveUser();

        Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.FINISH);
        alert.setHeaderText("Progress saved automatically");
		alert.showAndWait();

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
            Stage currentStage = (Stage) btnStart.getScene().getWindow();
            currentStage.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add your event handler methods and other controller logic here
}
