package de.apaschold.demo.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * <h2>ObstacleToken class</h2>
 * <li>Represents an obstacle in the game that the snake must avoid</li>
 * <li>Extends the {@link Token} class and includes a rectangular shape for rendering</li>
 */
public class ObstacleToken extends Token {

    //0.constants
    private static final double width = 8;
    private static final double height = 8;

    //1. attributes
    private final Rectangle shape;

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
