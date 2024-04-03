package com.group25;
import java.lang.Math;

/**
 * Represents a monster in the game.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class Monster {

    int maxHealth = 3;
    int currentHealth = maxHealth;
    int damage = 1;

    /**
     * Constructs a default monster with default stats.
     */
    // constructor method for default monster
    Monster() {
        // empty constructor for default monster stats
    }


    /**
     * Constructs a monster with custom starting health and damage.
     *
     * @param startingHealth the starting health of the monster
     * @param damage         the damage dealt by the monster
     */
    // constructor method for monster with custom stats
    Monster(int startingHealth, int damage) {
        this.maxHealth = startingHealth;
        currentHealth = maxHealth;
        this.damage = damage;
    }

    /**
     * Damages the monster by the specified amount.
     *
     * @param damage the amount of damage to be dealt
     */
    // method to damage the monster (called when player attacks)
    public void hurtMonster(int damage) {
        currentHealth = Math.max(currentHealth - damage, 0);
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    /**
     * Retrieves the current health of the monster.
     *
     * @return the current health of the monster
     */
    // getter for current monster health
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    /**
     * Retrieves the damage stat of the monster.
     *
     * @return the damage dealt by the monster
     */
    // getter for monster's damage stat
    public int getDamage() {
        return this.damage;
    }
}
