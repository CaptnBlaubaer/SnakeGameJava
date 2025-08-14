package de.apaschold.demo.logic;

import de.apaschold.demo.model.FoodToken;
import de.apaschold.demo.model.SnakeToken;
import de.apaschold.demo.model.Token;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Snake {
    //0. constants

    //1. attributes
    private ArrayList<SnakeToken> snakeTokens;
    private Direction direction;
    private SnakeToken head;// up, down, left, right

    //2. constructor
    public Snake() {
        this.snakeTokens = new ArrayList<>();
        this.direction = Direction.getRandomDirection();

        this.snakeTokens.add(new SnakeToken());
        this.head = this.snakeTokens.getFirst();
        int xCenter = this.head.getXCenter();
        int yCenter = this.head.getYCenter();

        this.snakeTokens.add(new SnakeToken(xCenter + 10, yCenter));
        this.snakeTokens.add(new SnakeToken(xCenter + 20, yCenter));
    }

    //3. getters and setters
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

    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    //4. methods
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

    public boolean eat(FoodToken foodToken){
        boolean hasEaten = false;

        if (this.head.intersects(foodToken)){
            this.snakeTokens.add(new SnakeToken(this.head.getXCenter(), this.head.getYCenter()));
            hasEaten = true;
        }

        return hasEaten;
    }

    public boolean checkCollisionWithWallsOrItself() {
        boolean collision = false;

        for (SnakeToken token : this.snakeTokens) {
            if (token != this.head && this.head.intersects(token)) {
                collision = true;
                break;
            }
        }

        return collision;
    }
}
