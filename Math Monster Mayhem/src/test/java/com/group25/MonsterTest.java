package com.group25;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonsterTest {

    private Monster defaultMonster;
    private Monster customMonster;

    @BeforeEach
    void setUp() {
        // Initialize a default monster and a custom monster before each test
        defaultMonster = new Monster();
        customMonster = new Monster(10, 3);
    }

    @Test
    void testDefaultMonsterHealth() {
        assertEquals(3, defaultMonster.getCurrentHealth(), "Default monster health should be 3");
    }

    @Test
    void testCustomMonsterHealth() {
        assertEquals(10, customMonster.getCurrentHealth(), "Custom monster health should be 10");
    }

    @Test
    void testDefaultMonsterDamage() {
        assertEquals(1, defaultMonster.getDamage(), "Default monster damage should be 1");
    }

    @Test
    void testCustomMonsterDamage() {
        assertEquals(3, customMonster.getDamage(), "Custom monster damage should be 3");
    }

    @Test
    void testHurtMonster() {
        // Damage the default monster with more damage than it has health to test it doesn't go below 0
        defaultMonster.hurtMonster(4);
        assertEquals(0, defaultMonster.getCurrentHealth(), "Monster health should not go below 0");

        // Damage the custom monster and check health
        customMonster.hurtMonster(5);
        assertEquals(5, customMonster.getCurrentHealth(), "Custom monster health after damage should be 5");
    }
}