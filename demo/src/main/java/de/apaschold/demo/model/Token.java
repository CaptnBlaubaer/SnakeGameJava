package de.apaschold.demo.model;

import java.util.Random;

public abstract class Character {

    protected int xCenter;
    protected int yCenter;

    public Character () {
        Random random = new Random();
        this.xCenter = random.nextInt(39) * 10 + 5; // Random x position between 5 and 395
        this.yCenter = random.nextInt(39) * 10 + 5; // Random y position between 5 and 395
    }

    public Character(int x, int y) {
        this.xCenter = x;
        this.yCenter = y;
    }

    public int getXCenter() {
        return xCenter;
    }

    public int getYCenter() {
        return yCenter;
    }

    public boolean intersects(Character other){
        // Default implementation can be overridden by subclasses
        return false;
    };
}
