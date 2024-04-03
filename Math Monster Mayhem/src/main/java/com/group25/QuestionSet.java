package com.group25;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Represents a set of questions.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class QuestionSet {
    
    private String name;
    private ArrayList<Question> questionSet;
    
    /**
     * Constructs a question set with the specified name.
     *
     * @param name the name of the question set
     */
    public QuestionSet(@JsonProperty("name") String name, @JsonProperty("questionSet") ArrayList<Question> questionSet){
        this.name = name;
        this.questionSet = questionSet;
    }
    public QuestionSet(String JSONname){

        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<ArrayList<Question>> typeReference = new TypeReference<>() {};
            ArrayList<Question> loadSet = mapper.readValue(new File("src/main/java/com/group25/GUI/assets/levels/" + JSONname + ".json"), typeReference);
            System.out.println(loadSet);

            this.questionSet = loadSet;

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Adds a multiple-choice question to the question set.
     *
     * @param question the multiple-choice question to add
     */
    public void addQuestion(Question question){

        if (this.questionSet == null) {
            this.questionSet = new ArrayList<>();
        }
        questionSet.add(question);
    }
    /**
     * Gets the ArrayList which contains the questions.
     *
     * @return the ArrayList containing the questions
     */
    public ArrayList<Question> getList(){

        return this.questionSet;
    }
    public Stack<Question> getListAsStack(){

        Stack<Question> questionStack = new Stack<>();

        questionStack.addAll(this.questionSet);

        return questionStack;
    }
    /**
     * Sets the ArrayList which contains the questions.
     *
     * @param newList an arraylist containing questions
     */
    public void setList(ArrayList<Question> newList){

        this.questionSet = newList;
    }
    /**
     * Gets the name of the question set.
     *
     * @return name of the question set
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the question set.
     *
     * @param name the new name of the question set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns an iterator for the question set.
     *
     * @return Iterator of the arrayList containing the questions
     */
    public Iterator<Question> iterator(){

        return questionSet.iterator();
    }
    
    /**
     * Sets the answers for a specific question in the question set.
     *
     * @param questionIndex the index of the question to set answers for
     * @param answers       the array of answers to set for the question
     */
    public void setAnswers(int questionIndex, Answer[] answers) {
        if (questionIndex >= 0 && questionIndex < questionSet.size()) {
            questionSet.get(questionIndex).setAnswers(answers);
        } else {
            System.err.println("Invalid question index.");
        }
    }

    /**
     * Saves the question set to a JSON file of a specified name.
     *
     * @param jsonName the name of the created JSON file 
     */
    public void saveQuestionSet(String jsonName){

        ObjectMapper mapper = new ObjectMapper();

        try {      
            mapper.writeValue(new File("src/main/java/com/group25/GUI/assets/levels/" + jsonName + ".json"), this.questionSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads the question set from a JSON file of a specified name.
     *
     * @param jsonName the name of the JSON file of which to load from
     */
    public void loadQuestionSet(String setName){

        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<ArrayList<Question>> typeReference = new TypeReference<>() {};
            ArrayList<Question> loadSet = mapper.readValue(new File("src/main/java/com/group25/GUI/assets/levels/" + setName + ".json"), typeReference);

            this.questionSet = loadSet;

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
