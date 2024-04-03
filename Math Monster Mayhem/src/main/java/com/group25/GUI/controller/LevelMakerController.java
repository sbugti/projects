package com.group25.GUI.controller;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.group25.Answer;
import com.group25.BubsFancyButtonBorder;
import com.group25.Question;
import com.group25.QuestionSet;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the Level Maker GUI.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class LevelMakerController implements Initializable {

    @FXML
    private BubsFancyButtonBorder backBtn;

    @FXML
    private ComboBox<Integer> levelBox;

    @FXML
    private ComboBox<Integer> questionBox;

    @FXML
    private TextField questionText;

    @FXML
    private TextField choiceAText;

    @FXML
    private TextField choiceBText;

    @FXML
    private TextField choiceCText;

    @FXML
    private TextField choiceDText;

    @FXML
    private RadioButton choiceACheck;

    @FXML
    private RadioButton choiceBCheck;

    @FXML
    private RadioButton choiceCCheck;

    @FXML
    private RadioButton choiceDCheck;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label labelFeedback;

    // Levels and questions
    private final ArrayList<Integer> levels = new ArrayList<>();
    private final ArrayList<Integer> questions = new ArrayList<>();
    private QuestionSet questionSet;

    /**
     * Initializes the controller after its root element has been completely processed.
     * 
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate levels
        for (int i = 1; i <= 5; i++) {
            levels.add(i);
        }
        levelBox.setItems(FXCollections.observableArrayList(levels));
    
        // Populate questions
        for (int i = 1; i <= 10; i++) {
            questions.add(i);
        }
        questionBox.setItems(FXCollections.observableArrayList(questions));
    
        // Set default selection
        levelBox.getSelectionModel().select(0); // Select Level 1
        questionBox.getSelectionModel().select(0); // Select Question 1
    
        // Load question data for default selection
        loadQuestionData(1, 1);
    
        // Add event handlers for combo box selection changes
        levelBox.setOnAction(event -> {
            int selectedLevel = levelBox.getSelectionModel().getSelectedItem();
            int selectedQuestion = questionBox.getSelectionModel().getSelectedItem();
            loadQuestionData(selectedLevel, selectedQuestion);
        });
    
        questionBox.setOnAction(event -> {
            int selectedLevel = levelBox.getSelectionModel().getSelectedItem();
            int selectedQuestion = questionBox.getSelectionModel().getSelectedItem();
            loadQuestionData(selectedLevel, selectedQuestion);
        });

        // Add event handlers to radio buttons
        choiceACheck.setOnAction(event -> {
            unselectAllExcept(choiceACheck);
        });

        choiceBCheck.setOnAction(event -> {
            unselectAllExcept(choiceBCheck);
        });

        choiceCCheck.setOnAction(event -> {
            unselectAllExcept(choiceCCheck);
        });

        choiceDCheck.setOnAction(event -> {
            unselectAllExcept(choiceDCheck);
        });
    }

        /**
     * Unselects all radio buttons except the given one.
     * 
     * @param selectedButton The radio button to remain selected.
     */
    private void unselectAllExcept(RadioButton selectedButton) {
        choiceACheck.setSelected(false);
        choiceBCheck.setSelected(false);
        choiceCCheck.setSelected(false);
        choiceDCheck.setSelected(false);
        
        selectedButton.setSelected(true);
    }

    /**
     * Loads question data and populates UI components.
     * 
     * @param level The level of the question.
     * @param questionNumber The number of the question.
     */
    private void loadQuestionData(int level, int questionNumber) {
        // Load question set from JSON file based on selected level
        String jsonFileName = "level" + level;
        questionSet = new QuestionSet(jsonFileName, new ArrayList<>()); // Create an empty question set

        // Load questions from JSON file
        questionSet.loadQuestionSet(jsonFileName);

        // Get the question from the loaded question set based on the selected question number
        ArrayList<Question> questions = questionSet.getList();
        if (questionNumber >= 1 && questionNumber <= questions.size()) {
            Question question = questions.get(questionNumber - 1); // Subtract 1 to convert to zero-based index

            // Populate UI components with question and answer options
            questionText.setText(question.getQuestion());
            Answer[] answers = question.getAnswers();
            if (answers.length >= 4) {
                choiceAText.setText(answers[0].getText());
                choiceBText.setText(answers[1].getText());
                choiceCText.setText(answers[2].getText());
                choiceDText.setText(answers[3].getText());

                // Select the correct answer in the radio buttons
                selectCorrectAnswer(question);
            } else {
                // Handle case where question does not have enough answers
                System.err.println("Question does not have enough answers.");
            }
        } else {
            // Handle invalid question number
            System.err.println("Invalid question number.");
        }
    }

    /**
     * Selects the correct answer in the radio buttons based on the provided question.
     * 
     * @param question The question to determine the correct answer.
     */
    private void selectCorrectAnswer(Question question) {
        Answer[] answers = question.getAnswers();
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].isCorrect()) {
                // Select the radio button corresponding to the correct answer
                switch (i) {
                    case 0:
                        unselectAllExcept(choiceACheck);
                        break;
                    case 1:
                        unselectAllExcept(choiceBCheck);
                        break;
                    case 2:
                    unselectAllExcept(choiceCCheck);
                        break;
                    case 3:
                        unselectAllExcept(choiceDCheck);
                        break;
                    default:
                        break;
                }
                break; // Break the loop once the correct answer is found
            }
        }
    }

    /**
     * Handles the action when the save button is clicked.
     */
    @FXML
    private void saveBtnClicked() {
        // Get the selected level
        int selectedLevel = levelBox.getSelectionModel().getSelectedItem();
        
        // Get the selected question
        int selectedQuestionIndex = questionBox.getSelectionModel().getSelectedIndex(); // Zero-based index
        
        // Retrieve the selected question from the question set
        Question selectedQuestion = questionSet.getList().get(selectedQuestionIndex);
        
        // Update the question text
        selectedQuestion.setQuestion(questionText.getText());
        
        // Update answers
        Answer[] answers = new Answer[4];
        answers[0] = new Answer(choiceAText.getText(), choiceACheck.isSelected());
        answers[1] = new Answer(choiceBText.getText(), choiceBCheck.isSelected());
        answers[2] = new Answer(choiceCText.getText(), choiceCCheck.isSelected());
        answers[3] = new Answer(choiceDText.getText(), choiceDCheck.isSelected());
        
        // Update the answers for the specific question
        selectedQuestion.setAnswers(answers);
        

        // Save the question set to a JSON file
        if (questionSet != null) {
            String jsonFileName = "level" + selectedLevel;
            questionSet.saveQuestionSet(jsonFileName);
            System.out.println("Question set saved successfully.");
        } else {
            System.err.println("No question set loaded.");
        }

        // Show labelFeedback for a second
        labelFeedback.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> labelFeedback.setVisible(false)));
        timeline.play();
    }    

    /**
     * Handles the action when the next button is clicked.
     */
    @FXML
    private void nextBtnClicked() {
        int selectedLevel = levelBox.getSelectionModel().getSelectedItem();
        int selectedQuestionIndex = questionBox.getSelectionModel().getSelectedIndex(); // Zero-based index
        
        // Check if there's a next question available
        if (selectedQuestionIndex < questionBox.getItems().size() - 1) {
            questionBox.getSelectionModel().select(selectedQuestionIndex + 1); // Select the next question
            loadQuestionData(selectedLevel, selectedQuestionIndex + 2); // Load data for the next question
        } else {
            // Check if there's a next level available
            if (selectedLevel < levels.size()) {
                levelBox.getSelectionModel().select(selectedLevel); // Select the next level
                questionBox.getSelectionModel().select(0); // Select the first question of the next level
                loadQuestionData(selectedLevel + 1, 1); // Load data for the first question of the next level
            }
        }
    }
    
    /**
     * Handles the action when the previous button is clicked.
     */
    @FXML
    private void prevBtnClicked() {
        int selectedLevel = levelBox.getSelectionModel().getSelectedItem();
        int selectedQuestionIndex = questionBox.getSelectionModel().getSelectedIndex(); // Zero-based index
        
        // Check if there's a previous question available
        if (selectedQuestionIndex > 0) {
            questionBox.getSelectionModel().select(selectedQuestionIndex - 1); // Select the previous question
            loadQuestionData(selectedLevel, selectedQuestionIndex); // Load data for the previous question
        } else {
            // Check if there's a previous level available
            if (selectedLevel > 1) {
                levelBox.getSelectionModel().select(selectedLevel - 2); // Select the previous level
                int lastQuestionIndex = questionBox.getItems().size() - 1;
                questionBox.getSelectionModel().select(lastQuestionIndex); // Select the last question of the previous level
                loadQuestionData(selectedLevel - 1, lastQuestionIndex + 1); // Load data for the last question of the previous level
            }
        }
    }
    
    /**
     * Handles the action when the back button is clicked.
     * 
     * @param event The action event generated by clicking the back button.
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
