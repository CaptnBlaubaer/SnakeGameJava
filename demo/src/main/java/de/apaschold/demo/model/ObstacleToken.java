package de.apaschold.demo.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ObstacleToken extends Token {

    //0.constants
    private static final int width = 8;
    private static final int height = 8;

    //1. attributes
    private Rectangle shape;

    //2. constructors
    public ObstacleToken() {
        super();
        this.shape = new Rectangle(super.xCenter - width / 2, super.yCenter - height / 2, width, height);
        this.shape.setFill(Color.LIGHTBLUE);
    }

    //3. getters and setters
    public Rectangle getShape() {
        return shape;
    }
}
