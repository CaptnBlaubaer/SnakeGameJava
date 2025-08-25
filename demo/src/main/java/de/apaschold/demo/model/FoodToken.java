package de.apaschold.demo.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * <h2>FoodToken class</h2>
 * <li>Represents a food item in the game that the snake can consume</li>
 * <li>Extends the {@link Token} class and includes a circular shape for rendering</li>
 */
public class FoodToken extends Token {
    //0. constants
    private static final int radius = 4;

    //1. attributes
    private final Circle shape;

    //2. constructors
    public FoodToken() {
        super();
        this.shape = new Circle(super.xCenter, super.yCenter, radius);
        this.shape.setFill(Color.RED);
    }

    //3. getters and setters
    public Circle getShape() {
        return shape;
    }
}
