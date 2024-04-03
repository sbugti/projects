package com.group25;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private Question question;
    private Answer[] answers;

    @BeforeEach
    void setUp() {
        answers = new Answer[]{
                new Answer("Answer 1", false),
                new Answer("Answer 2", true),
                new Answer("Answer 3", false)
        };
        question = new Question("What is the correct answer?", answers);
    }

    @Test
    void testGetQuestion() {
        assertEquals("What is the correct answer?", question.getQuestion(), "question text does not match");
    }

    @Test
    void testSetQuestion() {
        String newQuestionText = "What is the new question?";
        question.setQuestion(newQuestionText);
        assertEquals(newQuestionText, question.getQuestion(), "question text does not correctly update");
    }

    @Test
    void testGetAnswers() {
        assertArrayEquals(answers, question.getAnswers(), "answer getter incorrectly implemented");
    }

    @Test
    void testSetAnswers() {
        Answer[] newAnswers = new Answer[]{
                new Answer("New Answer 1", true),
                new Answer("New Answer 2", false)
        };
        question.setAnswers(newAnswers);
        assertArrayEquals(newAnswers, question.getAnswers(), "answer array incorrectly updated");
    }

    @Test
    void testCheckAnswerCorrect() {
        assertTrue(question.checkAnswer("Answer 2"), "checking true answer fails");
    }

    @Test
    void testCheckAnswerIncorrect() {
        assertFalse(question.checkAnswer("Answer 1"), "checking false answer fails");
    }

    @Test
    void testCheckAnswerNonExistent() {
        assertFalse(question.checkAnswer("Answer 100000"), "checking invalid answer fails");
    }
}
