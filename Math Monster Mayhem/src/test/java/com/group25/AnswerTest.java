package com.group25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AnswerTest {

    @Test
    public void testAnswerConstructorWithParams() {
        Answer answer = new Answer("correct test", true);
        assertEquals("correct test", answer.getText(), "text does not match expected");
        assertEquals(true, answer.isCorrect(), "correctness does not match expected");
    }

    @Test
    public void testAnswerConstructorDefaultCorrect() {
        Answer answer = new Answer("default should be true");
        assertEquals("default should be true", answer.getText(), "text does not match expected");
        assertEquals(true, answer.isCorrect(), "correctness does not match expected");
    }

    @Test
    public void testSetText() {
        Answer answer = new Answer("text", false);
        answer.setText("new text");
        assertEquals("new text", answer.getText(), "text not correctly updated");
    }

    @Test
    public void testSetCorrect() {
        Answer answer = new Answer("test answer", false);
        answer.setCorrect(true);
        assertEquals(true, answer.isCorrect(), "correctness not correctly updated");
    }
}
