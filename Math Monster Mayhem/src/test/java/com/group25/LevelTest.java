package com.group25;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class LevelTest {
    private Level level;
    private QuestionSet questionSet;

    @Before
    public void setUp() {
        questionSet = new QuestionSet("testset", null);
        level = new Level(45, 5, "Math", 15, questionSet);
    }

    @Test
    public void testConstructor() {
        assertEquals("Constructor timeAllowed", 45, level.getTimeAllowed());
        assertEquals("Constructor startingMonsterHealth", 5, level.getSMHealth());
        assertEquals("Constructor category", "Math", level.getCategory());
        assertEquals("Constructor numberOfStages", 15, level.getNumberOfStages());
        assertEquals("Constructor questionSets", questionSet, level.getQuestionSet());
    }

    @Test
    public void testSetTimeAllowed() {
        level.setTimeAllowed(60);
        assertEquals(60, level.getTimeAllowed());
    }

    @Test
    public void testSetSMHealth() {
        level.setSMHealth(10);
        assertEquals(10, level.getSMHealth());
    }

    @Test
    public void testSetCategory() {
        level.setCategory("Science");
        assertEquals("Science", level.getCategory());
    }

    @Test
    public void testSetNumberOfStages() {
        level.setNumberOfStages(20);
        assertEquals(20, level.getNumberOfStages());
    }

    @Test
    public void testSetQuestionSet() {
        QuestionSet newQuestionSet = new QuestionSet("testSet2", null);
        level.setQuestionSet(newQuestionSet);
        assertEquals(newQuestionSet, level.getQuestionSet());
    }
}