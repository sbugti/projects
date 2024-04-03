package com.group25;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

class QuestionGeneratorTest {

    private QuestionGenerator questionGenerator;
    private final int difficulty = 1;
    private final int[] range = {1, 10};

    @BeforeEach
    void setUp() {
        questionGenerator = new QuestionGenerator(difficulty, range);
    }

    @Test
    void testSetAndGetDifficulty() {
        questionGenerator.setDifficulty(2);
        assertEquals(2, questionGenerator.getDifficulty(), "difficulty setter/getter incorrect");
    }

    @Test
    void testSetAndGetRange() {
        int[] newRange = {5, 15};
        questionGenerator.setRange(newRange);
        assertArrayEquals(newRange, questionGenerator.getRange(), "range setter/getter incorrcet");
    }

    @Test
    void testGenerateQuestionEnsuresNonEmptyQuestionAndAnswers() {
        Question question = questionGenerator.generateQuestion();
        assertNotNull(question.getQuestion(), "question should not be empty");
        assertNotNull(question.getAnswers(), "answers should not be wmpty");
        assertTrue(question.getAnswers().length > 0, "there should be answers");
    }

    @Test
    void testGenerateQuestionEnsuresCorrectAnswerExists() {
        Question question = questionGenerator.generateQuestion();
        boolean correctAnswerFound = false;
        for (Answer answer : question.getAnswers()) {
            if (answer.isCorrect()) {
                correctAnswerFound = true;
                break;
            }
        }
        assertTrue(correctAnswerFound, "question needs at least one correct answer");
    }

    @Test
    void testGenerateQuestionProducesUniqueAnswers() {
        Question question = questionGenerator.generateQuestion();
        HashSet<String> uniqueAnswers = new HashSet<>();
        for (Answer answer : question.getAnswers()) {
            uniqueAnswers.add(answer.getText());
        }
        assertEquals(question.getAnswers().length, uniqueAnswers.size(), "answers should be unique");
    }
}
