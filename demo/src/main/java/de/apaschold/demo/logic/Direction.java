package de.apaschold.demo.logic;

import java.util.Random;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction getRandomDirection() {
        int randomOrdinal = new Random().nextInt(4);

        return Direction.values()[randomOrdinal];
    }
}
