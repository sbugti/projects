package com.group25;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuestionSetTest {

    private QuestionSet questionSet;
    private final String testSetName = "testQuestionSet";
    private final String testFilePath = "src/main/java/com/group25/GUI/assets/levels/" + testSetName + ".json";
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() { // generate a test question set
        questionSet = new QuestionSet("Sample Set", new ArrayList<>());
    }

    @BeforeEach
    public void setUpStreams() { // change stderr output to something we can check with junit
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() { // delete test file after each test
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    public void restoreStreams() { // change stderr back after test
        System.setErr(originalErr);
    }

    @Test
    void testAddAndListQuestions() {
        Question question = new Question("What is 2+2?", new Answer[]{new Answer("4", true)});
        questionSet.addQuestion(question);
        assertEquals(1, questionSet.getList().size(), "new questionset should only contain one question");
        assertEquals(question, questionSet.getList().get(0), "question doesn't match the added question");
    }

    @Test
    void testSetNameAndGetname() {
        String newName = "New Set Name";
        questionSet.setName(newName);
        assertEquals(newName, questionSet.getName(), "name does not correctly update");
    }

    @Test
    void testSaveAndLoadQuestionSet() throws Exception {
        Question question = new Question("What is 3+3?", new Answer[]{new Answer("6", true)});
        questionSet.addQuestion(question);
        questionSet.saveQuestionSet(testSetName);

        // load the question set
        QuestionSet loadedSet = new QuestionSet("test2");
        loadedSet.loadQuestionSet(testSetName);
        assertEquals(questionSet.getList().size(), loadedSet.getList().size(), "loading doesn't have corrcet number of questions");
        assertEquals(question.getQuestion(), loadedSet.getList().get(0).getQuestion(), "loaded questions dont match expected");
    }

    @Test
    void testSetAnswers() {
        Question question = new Question("What is 5+5?", new Answer[]{new Answer("10", true)});
        questionSet.addQuestion(question);
        Answer[] newAnswers = {new Answer("10", true), new Answer("11", false)};
        questionSet.setAnswers(0, newAnswers);
        assertArrayEquals(newAnswers, questionSet.getList().get(0).getAnswers(), "answers are not correctly updated");
    }

    
    @Test
    void testLoadQuestionSetWithInvalidFilePath() {
        new QuestionSet("woiefjmowhxfwuiehfw"); // try load file that doesn't exist
        String expectedMessagePart = "woiefjmowhxfwuiehfw.json"; // error message should contain this
        assertTrue(errContent.toString().contains(expectedMessagePart),"incorrect file error message not as expected");
    }
}
