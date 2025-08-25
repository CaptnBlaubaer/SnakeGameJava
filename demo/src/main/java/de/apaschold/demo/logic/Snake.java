package de.apaschold.demo.logic;

import de.apaschold.demo.model.FoodToken;
import de.apaschold.demo.model.ObstacleToken;
import de.apaschold.demo.model.SnakeToken;
import de.apaschold.demo.model.Token;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Snake class</h2>
 * <li>Contains a list of {@link SnakeToken}, representing the snake</li>
 * <li>Manages the snake's movement, growth, and collision detection</li>
 */

public class Snake {
    //0. constants

    //1. attributes
    private final ArrayList<SnakeToken> snakeTokens;
    private Direction direction;
    private SnakeToken head;// up, down, left, right

    //2. constructor
    public Snake() {
        this.snakeTokens = new ArrayList<>();
        this.direction = Direction.getRandomDirection();

        createSnake();
    }

    //3. getters and setters
    /**
     * <h2>getSnakeShape method</h2>
     * <li>Returns a list of {@link Rectangle} shapes representing the snake's tokens</li>
     * <li>Used for rendering the snake in the UI</li>
     * @return ArrayList of Rectangle shapes
     */
    public ArrayList<Rectangle> getSnakeShape() {
        ArrayList<Rectangle> shapes = new ArrayList<>();

        for (SnakeToken token : this.snakeTokens) {
            shapes.add(token.getShape());
        }

        return shapes;
    }

    public ArrayList<SnakeToken> getSnakeTokens() {
        return snakeTokens;
    }

    /** <h2>setDirection method</h2>
     * <li>Sets the snake's direction, preventing reversal</li>
     * @param newDirection The new direction to set
     */
    public void setDirection(Direction newDirection) {
        if (newDirection == Direction.UP && this.direction == Direction.DOWN
        || newDirection == Direction.DOWN && this.direction == Direction.UP
        || newDirection == Direction.LEFT && this.direction == Direction.RIGHT
        || newDirection == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        }

        this.direction = newDirection;
    }

    //4. methods
    /**
     * <h2>createSnake method</h2>
     * <li>Initializes the snake with a head and three body segments</li>
     * <li>Ensures the snake starts within the game boundaries</li>
     */
    private void createSnake() {
        this.snakeTokens.add(new SnakeToken());
        this.head = this.snakeTokens.getFirst();
        int xCenter = this.head.getXCenter();
        int yCenter = this.head.getYCenter();

        if (xCenter < 55 || yCenter < 55 || xCenter > 345 || yCenter > 345) {
            this.snakeTokens.clear();
            createSnake();

            return;
        }

        for (int i = 0; i < 3; i++) {
            switch (this.direction) {
                case UP -> yCenter += 10;
                case DOWN -> yCenter -= 10;
                case LEFT -> xCenter += 10;
                case RIGHT -> xCenter -= 10;
            }
            this.snakeTokens.add(new SnakeToken(xCenter, yCenter));
        }
    }

    /**
     * <h2>move method</h2>
     * <li>Describes the movement of the Snake</li>
     * Starts from the tail to move each snake token to the position of the previous snake token
     * Moves the head of the snake in the current active direction.
     */
    public void move(){
        //body movement
        for (int i = this.snakeTokens.size() - 1; i > 0; i--) {
            SnakeToken currentToken = this.snakeTokens.get(i);
            SnakeToken previousToken = this.snakeTokens.get(i - 1);

            // Move the current token to the position of the previous token
            currentToken.setXCenter(previousToken.getXCenter());
            currentToken.setYCenter(previousToken.getYCenter());
        }

        //headmovement
        int newXCenter = this.head.getXCenter();
        int newYCenter = this.head.getYCenter();

        switch (this.direction) {
            case UP -> this.head.setYCenter(newYCenter - 10);
            case DOWN -> this.head.setYCenter(newYCenter + 10);
            case LEFT -> this.head.setXCenter(newXCenter - 10);
            case RIGHT -> this.head.setXCenter(newXCenter + 10);
        }
    }

    /**
     * <h2>eat method</h2>
     * <li>Checks if the snake's head intersects with a {@link FoodToken}</li>
     * <li>If so, adds a new {@link SnakeToken} to the snake, simulating growth</li>
     * @param foodToken The FoodToken to check for intersection
     * @return true if the snake has eaten the food, false otherwise
     */
    public boolean eat(FoodToken foodToken){
        boolean hasEaten = false;

        if (this.head.intersects(foodToken)){
            this.snakeTokens.add(new SnakeToken(this.head.getXCenter(), this.head.getYCenter()));
            hasEaten = true;
        }

        return hasEaten;
    }

    /**
     * <h2>checkCollisionWithWallsOrItself method</h2>
     * <li>Iterates through the snake's tokens and a list of {@link ObstacleToken}</li>
     * @param obstacles List of ObstacleToken to check for collisions
     * @return true if a collision is detected, false otherwise
     */
    public boolean checkCollisionWithWallsOrItself(List<ObstacleToken> obstacles) {
        boolean collision = false;

        for (SnakeToken token : this.snakeTokens) {
            if (token != this.head && this.head.intersects(token)) {
                collision = true;
                break;
            }
        }

        for (Token obstacle : obstacles) {
            if (this.head.intersects(obstacle)) {
                collision = true;
                break;
            }
        }

        return collision;
    }
}
