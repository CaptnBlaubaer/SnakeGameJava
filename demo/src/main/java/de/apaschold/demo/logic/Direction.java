package de.apaschold.demo.logic;

import java.util.Random;

/**
 * <h2>Direction enum</h2>
 * <li>Represents the possible movement directions for the {@link Snake}</li>
 * <li>Includes a method to get a random direction</li>
 */
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
