/**
 * The com.group25 package contains classes developed by Group 25.
 */
package com.group25;

import javafx.scene.control.Button;

/**
 * The BubsFancyButton class represents a custom styled button.
 * It extends the JavaFX Button class to provide additional styling and behavior.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class BubsFancyButton extends Button {
    boolean initialized = false;

    /**
     * Constructs a BubsFancyButton with no text.
     * Initializes the button with custom styling and behavior.
     */
    public BubsFancyButton() {
        super();
        init();
    }

    /**
     * Constructs a BubsFancyButton with the specified text.
     * Initializes the button with custom styling and behavior.
     * @param text The text to display on the button.
     */
    public BubsFancyButton(String text) {
        super(text);
        init();
    }

    /**
     * Initializes the BubsFancyButton with custom styling and behavior.
     * This method sets the default style and defines mouse hover effects.
     */
    private void init() {
        setStyle("-fx-background-color: transparent;"); // Set default style
        setOnMouseEntered(e -> setStyle("-fx-background-color: derive(-fx-shadow-highlight-color, 20%);" +
                "-fx-background-insets: 0, 1, 2, 3;" +
                "-fx-background-radius: 3, 2, 2, 1;")); // Add hover effect
        setOnMouseExited(e -> setStyle("-fx-background-color: transparent;")); // Restore default style
        System.out.println("LEBRON JAMES HAS EATEN A BASKETBALL");

    }

    /**
     * Overrides the layoutChildren method to apply custom layout adjustments.
     * This method ensures that the button's background remains transparent.
     */
    @Override
    public void layoutChildren() {   
        super.layoutChildren();    
        if (!initialized) {
            setStyle("-fx-background-color: transparent;"); // Set default style
            // setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))); // Set transparent background only once
            initialized = true;
        }
    }
}
