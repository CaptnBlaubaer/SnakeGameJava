package de.apaschold.demo.model;

import java.util.Random;

/**
 * <h2>Token class</h2>
 * <li>Abstract base class for all tokens in the game ({@link SnakeToken}, {@link FoodToken}, {@link ObstacleToken})</li>
 * <li>Contains common attributes and methods for position and intersection detection</li>
 * <li>Provides constructors for random and specified positions</li>
 */
public abstract class Token {
    //0. constants

    // 1. attributes
    protected int xCenter;
    protected int yCenter;

    // 2. constructors
    public Token() {
        Random random = new Random();
        this.xCenter = random.nextInt(39) * 10 + 5; // Random x position between 5 and 395
        this.yCenter = random.nextInt(39) * 10 + 5; // Random y position between 5 and 395
    }

    public Token(int x, int y) {
        this.xCenter = x;
        this.yCenter = y;
    }

    // 3. getters and setters
    public int getXCenter() {
        return xCenter;
    }

    public int getYCenter() {
        return yCenter;
    }

    //4. methods
    /**
     * <h2>intersects method</h2>
     * <li>Checks if this token intersects with another token or the game border</li>
     * <li>Used for collision detection in the game</li>
     * @param other The other token to check intersection with
     * @return true if the tokens intersect or if this token is at the border, false otherwise
     */
    public boolean intersects(Token other){
        boolean isIntersecting = false;

        if (this.xCenter == other.xCenter && this.yCenter == other.yCenter) {
            isIntersecting = true; // Tokens intersect with other token
        }

        if (this.xCenter == -5 || this.yCenter == -5 || this.xCenter == 405 || this.yCenter == 405) {
            isIntersecting = true; // Token intersects with the border
        }

        return isIntersecting;
    }

    @Override
    public String toString() {
        return "Token{" +
                "xCenter=" + xCenter +
                ", yCenter=" + yCenter +
                '}';
    }
}
