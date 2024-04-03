package com.group25;
import java.lang.Math;

/**
 * Represents a player in the game.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class Player {

    int maxHealth = 10;
    int currentHealth = maxHealth;
    int damage = 1;
    Outfit appearance;

    /**
     * Constructs a player with the specified maximum health and damage.
     *
     * @param maxHealth the maximum health of the player
     * @param damage    the damage dealt by the player
     */
    // constructor method
    Player(int maxHealth, int damage) {
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
        this.damage = damage;

        this.appearance = new Outfit();
    }


    /**
     * Damages the player by the specified amount.
     *
     * @param damage the amount of damage to be dealt
     */
    // method to damage the player (called when monsters attack)
    public void hurtPlayer(int damage) {
        currentHealth = Math.max(currentHealth - damage, 0);
    }


    /**
     * Retrieves the current health of the player.
     *
     * @return the current health of the player
     */
    // getter for current player health
    public int getCurrentHealth() {
        return this.currentHealth;
    }


    /**
     * Retrieves the maximum health of the player.
     *
     * @return the maximum health of the player
     */
    // getter for player's max health
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Retrieves the damage stat of the player.
     *
     * @return the damage dealt by the player
     */
    // getter for player's damage stat
    public int getDamage() {
        return this.damage;
    }

    /**
     * Increases the player's stats when leveling up.
     */
    // level up to increase the player's stats
    public void levelUp() {
        adjustMaxHealth(5);
        adjustDamage(1);
    }

    /**
     * Adjusts the player's maximum health by the specified amount.
     *
     * @param healthAdjustment the amount to adjust the maximum health by
     */
    // helper function to modify the player's max HP
    private void adjustMaxHealth(int healthAdjustment) {
        this.maxHealth = maxHealth + healthAdjustment;
    }

    /**
     * Adjusts the player's damage stat by the specified amount.
     *
     * @param damageAdjustment the amount to adjust the damage stat by
     */
    // helper function to modify the player's damage stat
    private void adjustDamage(int damageAdjustment) {
        this.damage = damage + damageAdjustment;
    }

    /**
     * Retrieves the outfit of the player.
     *
     * @return the outfit of the player
     */
    // getter for player's outfit
    public Outfit getOutfit() {
        return this.appearance;
    }
}
