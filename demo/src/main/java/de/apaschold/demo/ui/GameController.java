package de.apaschold.demo.ui;

import de.apaschold.demo.logic.Direction;
import de.apaschold.demo.model.FoodToken;
import de.apaschold.demo.logic.Snake;
import de.apaschold.demo.model.SnakeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
    // TODO difficulty
    //TODO scoring system

    // 0. constants
    private static boolean paused = true;

    //1. attributes
    private Snake snake;
    private FoodToken foodToken;
    private int score = 0;
    private int difficulty = 1;

    @FXML
    private Label newGameLabel;

    @FXML
    private Pane gameWindow;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label difficultyLabel;

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
            case PLUS -> changeDifficulty(1);
            case MINUS -> changeDifficulty(-1);
            case N -> startNewGame();
            case ESCAPE -> Platform.exit();

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
            this.score += 1 + this.difficulty * 0.5;
        }

        updateGameWindow();
    }

    private void updateGameWindow() {
        gameWindow.getChildren().clear();
        gameWindow.getChildren().add(this.foodToken.getShape());
        gameWindow.getChildren().addAll(this.snake.getSnakeShape());

        scoreLabel.setText(this.score + "");
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

    private void changeDifficulty(int change) {
        this.difficulty += change;
        if (this.difficulty < 1) {
            this.difficulty = 1; // Ensure difficulty does not go below 1
        }

        difficultyLabel.setText(this.difficulty + "");
    }

    private void gameOver() {
        paused = true;
        gameWindow.getChildren().add(newGameLabel);
    }
}