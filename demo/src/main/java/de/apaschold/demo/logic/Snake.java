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
    private Direction direction; // up, down, left, right

    //2. constructor
    public Snake() {
        this.snakeTokens = new ArrayList<>();
        this.direction = Direction.getRandomDirection();

        this.snakeTokens.add(new SnakeToken());
        int xCenter = this.snakeTokens.get(0).getXCenter();
        int yCenter = this.snakeTokens.get(0).getYCenter();

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
        SnakeToken head = this.snakeTokens.getFirst();

        int newXCenter = head.getXCenter();
        int newYCenter = head.getYCenter();

        switch (this.direction) {
            case UP -> head.setYCenter(newYCenter - 10);
            case DOWN -> head.setYCenter(newYCenter + 10);
            case LEFT -> head.setXCenter(newXCenter - 10);
            case RIGHT -> head.setXCenter(newXCenter + 10);
        }
    }

    public boolean eat(FoodToken foodToken){
        boolean hasEaten = false;

        SnakeToken head = this.snakeTokens.getFirst();

        if (head.intersects(foodToken)){
            this.snakeTokens.add(new SnakeToken(head.getXCenter(), head.getYCenter()));
            hasEaten = true;
        }

        return hasEaten;
    }

    public boolean intersects(Token token) {
        for (SnakeToken snakeToken : this.snakeTokens) {
            if (snakeToken.intersects(token)) {
                return true;
            }
        }
        return false;
    }

    //public boolean checkCollisionWithWallsOrItself() {
        //SnakeToken head = this.snakeTokens.getFirst();
        //int xCenter = head.getXCenter();
        //int yCenter = head.getYCenter();


    //}
}
