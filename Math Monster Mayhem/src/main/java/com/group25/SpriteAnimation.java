package com.group25;

// package sandboxfx;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * A custom animation class for sprite animations.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    private int lastIndex;

    /**
     * Constructs a SpriteAnimation object.
     * @param imageView The ImageView to which the sprite animation is applied.
     * @param duration The duration of each cycle of the animation.
     * @param count The total number of frames in the sprite sheet.
     * @param columns The number of columns in the sprite sheet.
     * @param offsetX The horizontal offset of the first frame in the sprite sheet.
     * @param offsetY The vertical offset of the first frame in the sprite sheet.
     * @param width The width of each frame in the sprite sheet.
     * @param height The height of each frame in the sprite sheet.
     */
    public SpriteAnimation(
            ImageView imageView, 
            Duration duration, 
            int count,   int columns,
            int offsetX, int offsetY,
            int width,   int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    /**
     * Interpolates the animation based on the progress.
     * @param k The progress of the animation.
     */    
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}

