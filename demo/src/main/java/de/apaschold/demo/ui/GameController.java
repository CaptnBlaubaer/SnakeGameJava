package de.apaschold.demo.ui;

import de.apaschold.demo.logic.Direction;
import de.apaschold.demo.model.FoodToken;
import de.apaschold.demo.logic.Snake;
import de.apaschold.demo.model.SnakeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    //TODO implement game mechanics, such as collision detection, scoring, and game over conditions reset game.
    //TODO variable size
    //TODO implement a start screen and a game over screen
    //TODO starting conditions (not close to the wall,...)
    //TODO snake doesn't cross wall

    // 0. constants
    private static boolean paused = true;

    //1. attributes
    private Snake snake;
    private FoodToken foodToken;

    @FXML
    private Label newGameLabel;

    @FXML
    private Pane gameWindow;

    //2. initializer
    @Override
    public void initialize(URL location,ResourceBundle resources) {

        Timeline runGame = new Timeline(new KeyFrame(Duration.seconds(0.2), new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {

                if (!paused){
                    gameMechanics();
                }
            }
        }));
        runGame.setCycleCount(Timeline.INDEFINITE);
        runGame.play();
    }

    private void startNewGame() {
        this.snake = new Snake();
        createNewFoodToken();

        gameWindow.getChildren().addAll(this.snake.getSnakeShape());
        gameWindow.getChildren().add(foodToken.getShape());

        paused = !paused;
    }

    // 4. control method
    public void keyboardControl(KeyCode keyCode) {

        switch (keyCode) {
            case P -> paused = !paused;
            case W -> this.snake.setDirection(Direction.UP);
            case A -> this.snake.setDirection(Direction.LEFT);
            case S -> this.snake.setDirection(Direction.DOWN);
            case D -> this.snake.setDirection(Direction.RIGHT);
            case N -> startNewGame();

            default -> System.out.println("Key not recognized");
        }
    }

    //5. game mechanics
    private void gameMechanics(){
        // Here you would implement the game mechanics, such as moving the snake,
        // checking for collisions, and updating the game state.
        this.snake.move();

        if(this.snake.checkCollisionWithWallsOrItself()){
            gameOver();
            return; // Exit the method to stop further processing
        }

        if(this.snake.eat(this.foodToken)){
            createNewFoodToken();
        }

        updateGameWindow();
    }

    private void updateGameWindow() {
        gameWindow.getChildren().clear();
        gameWindow.getChildren().add(this.foodToken.getShape());
        gameWindow.getChildren().addAll(this.snake.getSnakeShape());
    }

    private void createNewFoodToken(){
        FoodToken newFoodToken = new FoodToken();

        // Ensure the new food token does not intersect with the snake tokens
        for (SnakeToken snakeToken : this.snake.getSnakeTokens()) {
            if (newFoodToken.intersects(snakeToken)) {
                createNewFoodToken();
                return; // Exit the method to ensure a new food token is created
            }
        }

        this.foodToken = newFoodToken;
    }

    private void gameOver() {
        paused = true;
        gameWindow.getChildren().add(newGameLabel);
    }
}