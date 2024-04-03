package com.group25;


/**
 * Represents a level in the game, containing information such as time allowed, starting monster health,
 * category, number of stages, and question sets.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class Level {
    private int timeAllowed = 30;
    private int startingMonsterHealth = 3;
    private String category;
    private int numberOfStages = 10;
    private QuestionSet questionSets;


    /**
     * Constructs a new level with the specified parameters.
     * 
     * @param timeAllowed the time allowed for the level
     * @param startingMonsterHealth the starting health of the monster in the level
     * @param category the category of the questions in the level
     * @param numberOfStages the number of stages in the level
     * @param questionSets the question sets associated with the level
     */
    public Level(int timeAllowed, int startingMonsterHealth, String category, int numberOfStages, QuestionSet questionSets){
        this.timeAllowed = timeAllowed;
        this.startingMonsterHealth = startingMonsterHealth;
        this.category = category;
        this.numberOfStages = numberOfStages;
        this.questionSets = questionSets;
    }


    /**
     * Gets the time allowed for the level.
     * 
     * @return the time allowed for the level
     */
    public int getTimeAllowed() {
        return this.timeAllowed;
    }


    /**
     * Sets the time allowed for the level.
     * 
     * @param newTime the new time allowed for the level
     */
    public void setTimeAllowed(int newTime) {
        timeAllowed = newTime;
    }


    /**
     * Gets the starting monster health for the level.
     * 
     * @return the starting monster health for the level
     */
    public int getSMHealth() {
        return this.startingMonsterHealth;
    }


    /**
     * Sets the starting monster health for the level.
     * 
     * @param SMHealth the new starting monster health for the level
     */
    public void setSMHealth(int SMHealth) {
        startingMonsterHealth = SMHealth;
    }


    /**
     * Gets the category of the level.
     * 
     * @return the category of the level
     */
    public String getCategory() {
        return this.category;
    }


    /**
     * Sets the category of the level.
     * 
     * @param newCategory the new category of the level
     */
    public void setCategory(String newCategory) {
        category = newCategory;
    }


    /**
     * Gets the number of stages in the level.
     * 
     * @return the number of stages in the level
     */
    public int getNumberOfStages() {
        return this.numberOfStages;
    }


    /**
     * Sets the number of stages in the level.
     * 
     * @param newNumber the new number of stages in the level
     */
    public void setNumberOfStages(int newNumber) {
        numberOfStages = newNumber;
    }


    /**
     * Gets the question sets associated with the level.
     * 
     * @return the question sets associated with the level
     */
    public QuestionSet getQuestionSet() {
        return this.questionSets;
    }

    /**
     * Sets the question sets associated with the level.
     * 
     * @param newSet the new question sets to be associated with the level
     */
    public void setQuestionSet(QuestionSet newSet) {
        questionSets = newSet;
    }
}
