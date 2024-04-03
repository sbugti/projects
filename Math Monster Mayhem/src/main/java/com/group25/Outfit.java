package com.group25;

/**
 * Represents an outfit with components such as hat, face, and shirt.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class Outfit {
    private int hat;    // Represents the hat component of the outfit
    private int face;   // Represents the face component of the outfit
    private int shirt;  // Represents the shirt component of the outfit

    /**
     * Constructs an outfit with default values for hat, face, and shirt.
     */
    public Outfit() {
        this(0, 0, 0);  // Default values for hat, face, and shirt
    }

    /**
     * Constructs an outfit with specified values for hat, face, and shirt.
     *
     * @param hat   The hat component of the outfit.
     * @param face  The face component of the outfit.
     * @param shirt The shirt component of the outfit.
     */
    public Outfit(int hat, int face, int shirt) {
        this.hat = hat;
        this.face = face;
        this.shirt = shirt;
    }

    /**
     * Gets the hat component of the outfit.
     *
     * @return The hat component.
     */
    public int getHat() {
        return hat;
    }

    /**
     * Sets the hat component of the outfit.
     *
     * @param hat The value to set for the hat component.
     */
    public void setHat(int hat) {
        this.hat = hat;
    }

    /**
     * Gets the face component of the outfit.
     *
     * @return The face component.
     */
    public int getFace() {
        return face;
    }

    /**
     * Sets the face component of the outfit.
     *
     * @param face The value to set for the face component.
     */
    public void setFace(int face) {
        this.face = face;
    }

    /**
     * Gets the shirt component of the outfit.
     *
     * @return The shirt component.
     */
    public int getShirt() {
        return shirt;
    }

    /**
     * Sets the shirt component of the outfit.
     *
     * @param shirt The value to set for the shirt component.
     */
    public void setShirt(int shirt) {
        this.shirt = shirt;
    }

    /**
     * Updates the outfit with new values provided in an array.
     *
     * @param outfitData An array containing new values for hat, face, and shirt.
     */
    public void updateOutfit(int[] outfitData) {
        // Ensure that the outfitData array has at least three elements
        if (outfitData.length >= 3) {
            hat = outfitData[0];
            face = outfitData[1];
            shirt = outfitData[2];
        }
    }

}
