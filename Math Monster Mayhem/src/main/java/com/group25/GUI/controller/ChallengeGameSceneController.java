package com.group25.GUI.controller;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import com.group25.Answer;
import com.group25.AudioPlayer;
import com.group25.AudioPlayer.SoundEffect;
import com.group25.Outfit;
import com.group25.Question;
import com.group25.QuestionGenerator;
import com.group25.SpriteAnimation;
import com.group25.User;
import com.group25.User.CurrentUser;
import com.group25.Utils;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for the game scene.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah
 */
public class ChallengeGameSceneController implements Initializable {

    
    // // Define default and hover styles
    // String defaultStyle = "-fx-background-color: #e0ffe0; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #484848;";
    // String hoverStyle = "-fx-background-color: #a0ffa0; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #484848;";

    @FXML
    private Label powerups;

    @FXML
    private Label timeText;
    private ScaleTransition scaleTransition;
    private ScaleTransition scoreTransition;

    @FXML
    private Label scoreText;

    @FXML
    private Label scoreMultiplyText;

    @FXML
    private Label loseScoreText;

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
    private AnchorPane losePane;

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
    private Button timeModifyBtn;

    @FXML
    private Button scoreMultiplyBtn;

    @FXML
    private Button reduceDmgBtn;

    @FXML
    private AnchorPane modifyPane;

    @FXML
    private AnchorPane slashAttackPane;

    @FXML
    private AnchorPane slashDefendPane;

    @FXML
    private Label modifyLabelText;

    @FXML
    private Label dmgMText;

    @FXML
    private Label timeMText;

    @FXML
    private Label scoreMText;

    @FXML
    private ImageView slashGIF;

    @FXML
    private Button btnTryAgain;

    private int dmgM = 0;
    private int timeM = 0;
    private int scoreM = 0;

    private String currentUsername;
    private User currentUser;
    private Question currentQuestion;
    private QuestionGenerator questionGenerator;
    private Answer[] answers;
    private AudioPlayer soundFX;

    private Timeline timeline = null;

    private int questionsAnswered = 0;
    private int questionsAnsweredCorrectly = 0;
    private int currentDifficulty = 1;
    boolean isPaused;

    private int scoreAllocatted = 100;
    private double damageTaken = 0.1;
    private int timeRemaining = 10;
    private int defaultTime = 10;
    private int addTime = 0;
    private double increaseMultiplier = 0;
    private int reduceDamage = 0;

    private boolean isModify = false;
    private boolean isPlaying = true;
    
    private int currIndex = 1;

    private String visualPath = "src/main/java/com/group25/GUI/assets/visuals/";

    private int index;

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root object, or {@code null} if the location is not known
     * @param resourceBundle the resources used to localize the root object, or {@code null} if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerHealth.setProgress(1.0);
        enemyHealth.setProgress(1.0);
        startPane.setDisable(false);
        btnStart.setDisable(false);
        startPane.setVisible(true);
        btnStart.setVisible(true);
        btnChoiceA.setDisable(true);
        btnChoiceB.setDisable(true);
        btnChoiceC.setDisable(true);
        btnChoiceD.setDisable(true);

        soundFX = new AudioPlayer(false);

        scoreText.setText("0");
        playerHealthText.setText(String.valueOf((int)(playerHealth.getProgress() * 100)) + " / 100");
        enemyHealthText.setText(String.valueOf((int)(enemyHealth.getProgress() * 100)) + " / 100");

        Button[] buttons = {btnChoiceA, btnChoiceB, btnChoiceC, btnChoiceD};
        for (Button button: buttons) {
            button.setStyle(Utils.defaultStyle); // Set default style);
            button.setOnMouseEntered(e -> button.setStyle(Utils.hoverStyle)); // Add hover effect
            button.setOnMouseExited(e -> button.setStyle(Utils.defaultStyle)); // Restore default style
        }

        dmgMText.setText("x" + dmgM);
        timeMText.setText("x" + timeM);
        scoreMText.setText("x" + scoreM);

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

        scoreTransition = new ScaleTransition(Duration.seconds(0.1), scoreText);
        scoreTransition.setFromX(1.0);
        scoreTransition.setFromY(1.0);
        scoreTransition.setToX(0.9);
        scoreTransition.setToY(0.9);
        scoreTransition.setAutoReverse(true);
        scoreTransition.setOnFinished(event -> {
            scoreText.setScaleX(1.0);
            scoreText.setScaleY(1.0);
        });
    }

    public void loadQuestion() {
        timeRemaining = defaultTime;
        timeText.setText(Integer.toString(timeRemaining));
        timeText.setTextFill(Color.WHITE);
        this.timeline.play(); // Start the timer

        // Increment questionsAnswered counter
        questionsAnswered++;

        // Increment difficulty every 5 questions
        if (questionsAnswered % 5 == 0) {
            currentDifficulty++;
            questionGenerator.setDifficulty(currentDifficulty);
        }
    
        // Rest of your method remains the same...
        // Create a QuestionGenerator object
        questionGenerator = new QuestionGenerator(currentDifficulty, new int[]{1, 10});
    
        // Generate a question
        Question currentQuestion = questionGenerator.generateQuestion();
    
        questionText.setText(currentQuestion.getQuestion() + " ?");
        answers = currentQuestion.getAnswers();
        btnChoiceA.setText("1)   " + answers[0].getText());
        btnChoiceB.setText("2)   " + answers[1].getText());
        btnChoiceC.setText("3)   " + answers[2].getText());
        btnChoiceD.setText("4)   " + answers[3].getText());
        timeline.play(); // Start the timer
    }

    /**
     * plays a monster noise 
     * @param death bool to check if dead
     * 
     */
    void playMonsterNoise(boolean death){
        
        try {
            new Thread(()-> {        
                
                if (index == 1) {

                    if (death) {  
                        
                        this.soundFX.play(SoundEffect.SwordDeath);
                    }
                    else{
                        this.soundFX.play(SoundEffect.Sword);
                    }
                }
                else if (index == 2) {

                    if (death) {  
                        this.soundFX.play(SoundEffect.GhostDeath);
                    }
                    else{
                        this.soundFX.play(SoundEffect.Ghost);
                    }
                }
                else if (index == 3) {

                    if (death) {  
                        this.soundFX.play(SoundEffect.GenieDeath);
                    }
                    else{
                        this.soundFX.play(SoundEffect.Genie);
                    }
                }
                else if (index == 4) {

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

    /**
     * check if answer correct
     * @param btnClicked -- btn clicked
     */
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

    /**
     * get user score
     * 
     * @param str string for score
     */
    private int getScore(String str) {
        return Integer.parseInt(str.replaceAll("[^\\d]", ""));
    }

    void loseScreen() {
        isPlaying = false;
        this.timeline.stop();
        losePane.setDisable(false);
        losePane.setVisible(true);
        btnLoseBack.setDisable(false);
        btnLoseBack.setVisible(true);
        btnTryAgain.setDisable(false);
        btnTryAgain.setVisible(true);

        // Setting High Score
        int oldScore = this.currentUser.getScore();
        int newScore = getScore(scoreText.getText());
        
        if (newScore > oldScore) {
            this.currentUser.setScore(newScore);
            this.currentUser.saveUser();
        }
        this.soundFX.play(SoundEffect.LevelDefeat);
        System.out.println("BOMBOCLAAATT");
    }

    /**
     * set up slash anim from spritesheet
     */
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

    /**
     * calculate the score
     * @return score int
     */
    int calculateScore() {
        return timeRemaining * 10 + scoreAllocatted;
    }

    /**
     * vibrate used to show attack effect
     * @param f
     * @param shakeLength
     * @param shakeIntensity
     */
    public void vibrate(Node f, int shakeLength, int shakeIntensity) {

        try {
            new Thread(()->{
                final int originalX = (int) ((ImageView) f).getX(); // Get initial position

                TranslateTransition shake = new TranslateTransition(Duration.millis(shakeLength), f);
                shake.setFromX(originalX);
                shake.setByX(shakeIntensity);
                shake.setCycleCount(4); // Shake in each direction twice
                shake.setAutoReverse(true);

                shake.play();

            }){{start();}}.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * check correct choice made
     */
    void correctChoiceMade() {
        this.soundFX.play(SoundEffect.CorrectAnswer);
        enemyHealth.setProgress(enemyHealth.getProgress() - 0.1);
        scoreText.setText("Score: " + String.valueOf(getScore(scoreText.getText()) + calculateScore()));
        setupSlash();
        playMonsterNoise(false);
        vibrate(enemyImg, 125, 25);
        vibrate(shadowImg, 125, 25);
        enemyHealthText.setText(String.valueOf((int)(Math.round(enemyHealth.getProgress() * 1000.0) / 10.0)) + " / 100");

        scoreTransition.playFromStart();

        if (Math.round(enemyHealth.getProgress() * 100.0) / 100.0 <= 0) {
            setRandomEnemyImage();
            isModify = true;
        }
    }


    /**
     * check incorrect choice made
     */
    void incorrectChoiceMade() {
        this.soundFX.play(SoundEffect.IncorrectAnswer);
        playerHealth.setProgress(playerHealth.getProgress() - damageTaken);
        playerHealthText.setText(String.valueOf((int)(Math.round(playerHealth.getProgress() * 1000.0) / 10.0)) + " / 100");

        this.soundFX.play(SoundEffect.Person);
        vibrate(shirtImg, 125, 25);
        vibrate(skinImg, 125, 25);
        vibrate(hatImg, 125, 25);
        
        if (Math.round(playerHealth.getProgress() * 1000.0) / 100.0 <= 0) {
            Platform.runLater(()->loseScreen());
            Platform.runLater(()->loseScoreText.setText(String.valueOf(getScore(scoreText.getText()))));
        }
    }

    /**
     * set enemy to a random sprite
     */
    private void setRandomEnemyImage() {
        String basePath = visualPath + "monsters/";
        Random random = new Random();
        do {
            index = random.nextInt(5) + 1;
        } while (index == currIndex); // Ensure new enemy is different
        String enemyPath = basePath + "enemy" + index + ".png";
        setImageFromPath(enemyImg, enemyPath);
        currIndex = index;
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
        if (isPaused) return;

        if (isCorrect('a')) {
            correctChoiceMade();
        }
        else {
            incorrectChoiceMade();
        }       
        loadQuestion();
    }

    /**
     * Event handler method for when Choice button b is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnChoiceBClicked(ActionEvent event) {
        if (isPaused) return;

        if (isCorrect('b')) {
            correctChoiceMade();
        }
        else {
            incorrectChoiceMade();
        }  
        loadQuestion();
    }

    /**
     * Event handler method for when Choice button c is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnChoiceCClicked(ActionEvent event) {
        if (isPaused) return;

        if (isCorrect('c')) {
            correctChoiceMade();
        }
        else {
            incorrectChoiceMade();
        }  
        loadQuestion();
    }

    /**
     * Event handler method for when Choice button d is clicked.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void btnChoiceDClicked(ActionEvent event) {
        if (isPaused) return;

        if (isCorrect('d')) {
            correctChoiceMade();
        }
        else {
            incorrectChoiceMade();
        }  
        loadQuestion();

    }

    /**
     * initiate powersups
     */
    void initiateModifier() {
        isPaused = true;
        timeline.stop();

        timeModifyBtn.setDisable(false);
        timeModifyBtn.setVisible(true);
        scoreMultiplyBtn.setDisable(false);
        scoreMultiplyBtn.setVisible(true);
        reduceDmgBtn.setDisable(false);
        reduceDmgBtn.setVisible(true);

        btnPause.setDisable(true);
        btnChoiceA.setDisable(true);
        btnChoiceB.setDisable(true);
        btnChoiceC.setDisable(true);
        btnChoiceD.setDisable(true);

        modifyPane.setVisible(true);
        modifyPane.setDisable(false);
        modifyLabelText.setVisible(true);
        modifyLabelText.setDisable(false);

        Random random = new Random();

        addTime =  random.nextInt(5) + 1;
        timeModifyBtn.setText("Increase Time Given By " + addTime);

        increaseMultiplier = 0.1 + random.nextDouble() * (0.5 - 0.1);
        // Round the random number to one decimal place
        increaseMultiplier = Math.round(increaseMultiplier * 10.0) / 10.0;
        scoreMultiplyBtn.setText("Increase Score Multiplier By " + increaseMultiplier);

        reduceDamage = random.nextInt(3) + 1;
        // Round the random number to one decimal place
        reduceDmgBtn.setText("Reduce Damage Taken By " + reduceDamage); 
        if (damageTaken <= 0.01) {
            reduceDmgBtn.setDisable(true);
        } 


    }

    /**
     * end powerup effect
     */
    void endModifier() {
        isModify = false;
        isPaused = false;
        timeline.play();

        dmgMText.setText("x" + dmgM);
        timeMText.setText("x" + timeM);
        scoreMText.setText("x" + scoreM);

        timeModifyBtn.setDisable(true);
        timeModifyBtn.setVisible(false);
        scoreMultiplyBtn.setDisable(true);
        scoreMultiplyBtn.setVisible(false);
        reduceDmgBtn.setDisable(true);
        reduceDmgBtn.setVisible(false);

        btnPause.setDisable(false);
        btnChoiceA.setDisable(false);
        btnChoiceB.setDisable(false);
        btnChoiceC.setDisable(false);
        btnChoiceD.setDisable(false);

        modifyPane.setVisible(false);
        modifyPane.setDisable(true);
        modifyLabelText.setVisible(false);
        modifyLabelText.setDisable(true);
    }

    /**
     * handle pause event button
     * @param event
     */
    @FXML
    void btnPauseClicked(ActionEvent event) {
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

    /**
     * handle continue event button
     * @param event
     */
    @FXML
    void btnContinueClicked(ActionEvent event) {
        isPaused = false;

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

    /**
     * handle update timer
     * @param time curr time
     */
    public void updateTimer(int time) {
        // timeText.setText(Integer.toString(time));
        scaleTransition.playFromStart(); // Play the animation

        if (timeRemaining <= 5) {
            timeText.setTextFill(Color.RED);
        } else {
            timeText.setTextFill(Color.WHITE);
        }
    }

    /**
     * handle event start button click
     * @param event
     */
    @FXML
    void btnStartClicked(ActionEvent event) {

        try {
            currentUsername = CurrentUser.loadFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentUser = new User(currentUsername, timeRemaining, null);
        currentUser.loadUser(currentUsername);

        btnStart.setVisible(false);
        btnStart.setDisable(true);
        startPane.setVisible(false);
        startPane.setDisable(true);


        btnChoiceA.setDisable(false);
        btnChoiceB.setDisable(false);
        btnChoiceC.setDisable(false);
        btnChoiceD.setDisable(false);

        scoreText.setText("Score: 0");
        setOutfit();
        setRandomEnemyImage();


        if (this.timeline == null) {
    
            this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), timelineEvent -> {
                if (isModify) {
                    initiateModifier();
                }
                timeRemaining--;

                if (timeRemaining < 0) {
                    // Handle timeout
                    playerHealth.setProgress(playerHealth.getProgress() - damageTaken);
                    playerHealthText.setText(String.valueOf((int)(Math.round(playerHealth.getProgress() * 1000.0) / 10.0)) + " / 100");

                    if (isPlaying) {  
                        loadQuestion(); // Stop the timer
                    }
                    // Add your logic for timeout here
                }
                else {
                    timeText.setText(Integer.toString(timeRemaining));
                    updateTimer(timeRemaining);
                }
            }));

            this.timeline.setCycleCount(this.timeline.INDEFINITE); // Repeat indefinitely
        }
        else {
            timeline.stop();
            timeRemaining = defaultTime;
        }

        loadQuestion();
    }

    /**
     * set player outfit
     */
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

    /**
     * handle menu button click event
     * @param event
     */
    @FXML
    void btnMenuClicked(ActionEvent event) {

        timeline.stop();
        soundFX.stop();
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

    /**
     * handle continue button clicked event
     * @param event
     */
    @FXML
    void btnTryAgainClicked(ActionEvent event) {
        btnTryAgain.setDisable(false);
        btnTryAgain.setVisible(true);
        btnLoseBack.setVisible(false);
        btnLoseBack.setDisable(true);
        losePane.setVisible(false);
        losePane.setDisable(true);

        isPaused = false;
        playerHealthText.setText(String.valueOf((int)(playerHealth.getProgress() * 100)) + " / 100");

        initialize(null, null);

        btnChoiceA.setDisable(false);
        btnChoiceB.setDisable(false);
        btnChoiceC.setDisable(false);
        btnChoiceD.setDisable(false);
        soundFX = new AudioPlayer(false);

    }

    /**
     * handle keyboard input
     * @param event
     */
    @FXML
    void handleKeyPress(KeyEvent event){
        KeyCode pressedCode = event.getCode();
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

    /**
     * handle timer modify event
     * @param event
     */
    @FXML
    void timeModifyBtnClicked(ActionEvent event) {
        defaultTime += addTime;
        timeRemaining = defaultTime;
        timeM++;

        endModifier();
    }

    /**
     * handle score multiplier event
     * @param event
     */
    @FXML
    void scoreMultiplyBtnClicked(ActionEvent event) {
        scoreAllocatted += (int)(increaseMultiplier * 100);
        System.out.println(scoreAllocatted);
        scoreMultiplyText.setText(String.format("%.1f", (double) scoreAllocatted / 100) + "x");
        scoreM++;

        endModifier();
    }

    /**
     * handle dmg taken event
     * @param event
     */
    @FXML
    void reduceDmgBtnClicked(ActionEvent event) {
        damageTaken -= (double) reduceDamage / 100;
        damageTaken = Math.round(damageTaken * 100.0) / 100.0;
        dmgM++;

        endModifier();
    }

    
}