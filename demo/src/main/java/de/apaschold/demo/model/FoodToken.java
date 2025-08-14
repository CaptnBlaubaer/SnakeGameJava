package de.apaschold.demo.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FoodToken extends Token {
    //0. constants
    private static final int radius = 4;

    //1. attributes
    private Circle shape;

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
