package com.group25;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a multiple-choice question.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class Question{

    private Answer[] answers;
    private String question; 

    /**
     * Constructs a new multiple-choice question with the given question and array of answers.
     *
     * @param question the question text
     * @param answers  an array of Answer objects representing the possible choices
     */
    public Question(@JsonProperty("question") String question, @JsonProperty("answer") Answer[] answers){

        this.question = question;
        this.answers = answers;
    }

    /**
     * Retrieves the text of the question.
     *
     * @return the question text
     */
    public String getQuestion(){

        return this.question;
    }

    /**
     * Retrieves the array of Answer objects representing the possible choices.
     *
     * @return an array of Answer objects
     */

    /**
     * Sets the question text of this question.
     *
     * @param question the new question text
     */
    public void setQuestion(String question) {
        this.question = question;
    }


    public Answer[] getAnswers(){
        return this.answers;
    }
    
    public void setAnswers(Answer[] answers){

        this.answers = answers;
    }

    /**
     * Checks if a given response is the correct answer for this question
     * @param response the response to check
     * @return true if the response matches the correct answer, false otherwise
     */
    public boolean checkAnswer(String response) {

        for (Answer answer : answers) {
            if (answer.getText().equals(response) && answer.isCorrect()) {
                return true;
            }
        }
        return false;
    }
}
