package com.group25;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        // creating player object before every test
        player = new Player(10, 1);
    }

    @Test
    void testPlayerInitializationWithParameters() {
        assertEquals(10, player.getMaxHealth(), "max health not correctly initialized.");
        assertEquals(10, player.getCurrentHealth(), "current health should match max health by default");
        assertEquals(1, player.getDamage(), "damage not correctly initialized.");
        assertNotNull(player.getOutfit(), "default outfit should not be null");
    }

    @Test
    void testHurtPlayerReducesHealth() {
        player.hurtPlayer(4);
        assertEquals(6, player.getCurrentHealth(), "player does not correctly take damage");
    }

    @Test
    void testHurtPlayerDoesNotReduceHealthBelowZero() {
        player.hurtPlayer(15);
        assertEquals(0, player.getCurrentHealth(), "player health should not become negative");
    }

    @Test
    void testLevelUpIncreasesStats() {
        player.levelUp();
        assertEquals(15, player.getMaxHealth(), "level should result in hp+5");
        assertEquals(2, player.getDamage(), "level should result in attack+1");
    }

    @Test
    void testGetMaxHealthReturnsCorrectValue() {
        assertEquals(10, player.getMaxHealth(), "getmaxhealth not working");
    }

    @Test
    void testGetCurrentHealthReturnsCorrectValue() {
        player.hurtPlayer(3);
        assertEquals(7, player.getCurrentHealth(), "getcurrenthealth not working after taking damage.");
    }

    @Test
    void testGetDamageReturnsCorrectValue() {
        assertEquals(1, player.getDamage(), "getDamage not returning correct value");
    }

    @Test
    void testGetOutfitReturnsNonNullOutfit() {
        assertNotNull(player.getOutfit(), "getOutfit should return outfit object");
    }

    @Test
    void testHurtPlayerWithNoDamage() {
        player.hurtPlayer(0);
        assertEquals(10, player.getCurrentHealth(), "player hp shouldn't change when hurt for 0");
    }
}
