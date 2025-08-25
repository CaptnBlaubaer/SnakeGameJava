package de.apaschold.demo.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * <h2>SnakeToken class</h2>
 * <li>Represents a segment of the snake's body in the game</li>
 * <li>Extends the {@link Token} class and includes a rectangular shape for rendering</li>
 * <li>Compared to other {@link Token} possesses setters to change position</li>
 */

public class SnakeToken extends Token {

    //0.constants
    private static final double width = 8;
    private static final double height = 8;

    //1. attributes
    private final Rectangle shape;

    //2. constructors
    public SnakeToken() {
        super();
        this.shape = new Rectangle(super.xCenter - width / 2, super.yCenter - height / 2, width, height);
        this.shape.setFill(Color.GREEN);
    }

    public SnakeToken(int xCenter, int yCenter) {
        super(xCenter, yCenter);
        this.shape = new Rectangle(xCenter - 4, yCenter - 4, width, height);
        this.shape.setFill(Color.GREEN);
    }

    //3. getters and setters
    public Rectangle getShape() {
        return shape;
    }

    public void setXCenter(int xCenter) {
        this.xCenter = xCenter;
        this.shape.setX(xCenter - width / 2);
    }

    public void setYCenter(int yCenter) {
        this.yCenter = yCenter;
        this.shape.setY(yCenter - height / 2);
    }

}
