package com.group25;

import javafx.scene.control.Button;

/**
 * The BubsFancyButtonBorder class represents a custom styled button.
 * It extends the JavaFX Button class to provide additional styling and behavior.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class BubsFancyButtonBorder extends Button {
    boolean initialized = false;

    /**
     * Constructs a BubsFancyButton with no text.
     * Initializes the button with custom styling and behavior.
     */
    public BubsFancyButtonBorder() {
        super();
        init();
    }

    /**
     * Constructs a BubsFancyButton with the specified text.
     * Initializes the button with custom styling and behavior.
     * @param text The text to display on the button.
     */
    public BubsFancyButtonBorder(String text) {
        super(text);
        init();
    }
    /**
     * Initializes the BubsFancyButton with custom styling and behavior.
     * This method sets the default style and defines mouse hover effects.
     */
    private void init() {
        // Set default style with an outline (border)
        this.setStyle("-fx-background-color: transparent; " +
                      "-fx-border-color: white; " + // White border color
                      "-fx-border-width: 2; " + // Border width of 2 pixels
                      "-fx-border-radius: 5;"); // Rounded border with a radius of 5

        // Hover effect to change background but maintain border
        setOnMouseEntered(e -> setStyle("-fx-background-color: derive(-fx-shadow-highlight-color, 20%);" +
                                        "-fx-background-insets: 0, 1, 2, 3;" +
                                        "-fx-background-radius: 3, 2, 2, 1;" +
                                        "-fx-border-color: white; " +
                                        "-fx-border-width: 2; " +
                                        "-fx-border-radius: 5;"));
        setOnMouseExited(e -> setStyle("-fx-background-color: transparent; " +
                                       "-fx-border-color: white; " +
                                       "-fx-border-width: 2; " +
                                       "-fx-border-radius: 5;"));

        System.out.println("LEBRON JAMES HAS EATEN A BASKETBALL");
    }

    /**
     * Overrides the layoutChildren method to apply custom layout adjustments.
     * This method ensures that the button's background remains transparent.
     */
    @Override
    public void layoutChildren() {   
        super.layoutChildren();
        // The check ensures the custom style is set only once
        if (!initialized) {
            this.setStyle("-fx-background-color: transparent; " +
                          "-fx-border-color: white; " +
                          "-fx-border-width: 2; " +
                          "-fx-border-radius: 5;");
            initialized = true;
        }
    }
}
