package com.group25;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Answer class represents a possible answer in a quiz.
 * It contains the text of the answer and a flag indicating whether it is correct or not.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class Answer {
    
    private String text;
    private boolean correct;

    /**
     * Constructs an Answer object with the specified text and correctness.
     *
     * @param text    The text of the answer.
     * @param correct true if the answer is correct, false otherwise.
     */
    public Answer(@JsonProperty("text") String text, @JsonProperty("correct") boolean correct){
        this.text = text;
        this.correct = correct;
    }

    /**
     * Constructs an Answer object with the specified text, assuming it is correct by default.
     *
     * @param text The text of the answer.
     */
    public Answer(String text){
        this.text = text;
        this.correct = true;
    }

    /**
     * Gets the text of the answer.
     *
     * @return The text of the answer.
     */
    public String getText(){
        return this.text;
    }

    /**
     * Sets the text of the answer.
     *
     * @param text The text of the answer.
     */
    public void setText(String text){
        this.text = text;
    }

    /**
     * Sets whether the answer is correct or not.
     *
     * @param correct true if the answer is correct, false otherwise.
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
    
    /**
     * Checks if the answer is correct.
     *
     * @return true if the answer is correct, false otherwise.
     */
    public boolean isCorrect(){
        return this.correct;
    }
}
