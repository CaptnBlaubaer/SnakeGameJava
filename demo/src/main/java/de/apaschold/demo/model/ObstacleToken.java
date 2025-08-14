package de.apaschold.demo.model;

public class ObstacleToken extends Token {

    private static final int width = 10;
    private static final int height = 10;

    public ObstacleToken() {
        super();
    }

    public ObstacleToken(int xCenter, int yCenter) {
        super(xCenter, yCenter);
    }

    @Override
    public String toString() {
        return "ObstacleToken{" +
                "xCenter=" + xCenter +
                ", yCenter=" + yCenter +
                '}';
    }
}
