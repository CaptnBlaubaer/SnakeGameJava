package de.apaschold.demo.model;

import java.util.Random;

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
    public boolean intersects(Token other){
        boolean isIntersecting = false;

        if (this.xCenter == other.xCenter && this.yCenter == other.yCenter) {
            isIntersecting = true;
        }

        if (this.xCenter == 0 || this.yCenter ==0 || this.xCenter == 400 || this.yCenter == 400) {
            isIntersecting = true; // Token intersects with the border
        }

        return isIntersecting;
    };
}
