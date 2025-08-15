package de.apaschold.demo.ui;

import de.apaschold.demo.logic.Direction;
import de.apaschold.demo.model.FoodToken;
import de.apaschold.demo.logic.Snake;
import de.apaschold.demo.model.ObstacleToken;
import de.apaschold.demo.model.SnakeToken;
import de.apaschold.demo.model.Token;
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
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;

public class GameController implements Initializable {

    //TODO implement game mechanics, such as collision detection reset game.
    //TODO variable size
    //TODO implement a start screen and a game over screen
    //TODO snake doesn't cross wall
    //TODO difficulty

    // 0. constants
    private static final double SPEED = 0.1;
    private static final double DIFFICULTY_MULTIPLIER = 0.5;// Speed of the game in seconds

    //1. attributes
    private Snake snake;
    private FoodToken foodToken;
    private double score = 0;
    private int difficulty = 0;
    private static boolean paused = true;
    private List<ObstacleToken> obstacles;


    @FXML
    private Label newGameLabel;

    @FXML
    private Pane gameWindow;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label difficultyLabel;

    @FXML
    private Label highscoreLabel;

    //2. initializer
    @Override
    public void initialize(URL location,ResourceBundle resources) {
        this.obstacles = new ArrayList<>();
        fillHighscoreList();

        Timeline runGame = new Timeline(new KeyFrame(Duration.seconds(SPEED), new EventHandler<>() {
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
            case N -> {if(paused == true) {startNewGame();}}
            case ESCAPE -> Platform.exit();

            default -> System.out.println("Key not recognized: " + keyCode);
        }
    }


    private void startNewGame() {
        this.snake = new Snake();

        this.obstacles.clear();
        for (int i = 0; i < this.difficulty * 6; i++){
            obstacles.add(createObstacle());
        }

        createNewFoodToken();



        paused = !paused;
    }

    //5. game mechanics
    private void gameMechanics(){
        // Here you would implement the game mechanics, such as moving the snake,
        // checking for collisions, and updating the game state.
        this.snake.move();

        if(this.snake.checkCollisionWithWallsOrItself(this.obstacles)){
            gameOver();
            return; // Exit the method to stop further processing
        }

        if(this.snake.eat(this.foodToken)){
            createNewFoodToken();
            this.score += 1 + this.difficulty * DIFFICULTY_MULTIPLIER; // Increase score based on difficulty
        }

        updateGameWindow();
    }

    private void updateGameWindow() {
        gameWindow.getChildren().clear();
        gameWindow.getChildren().add(this.foodToken.getShape());
        gameWindow.getChildren().addAll(this.snake.getSnakeShape());

        for (ObstacleToken obstacle : this.obstacles) {
            gameWindow.getChildren().add(obstacle.getShape());
        }

        scoreLabel.setText((int) Math.floor(this.score) + "");
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

        for (ObstacleToken obstacle : this.obstacles) {
            if (newFoodToken.intersects(obstacle)) {
                createNewFoodToken();
                return; // Exit the method to ensure a new food token is created
            }
        }

        this.foodToken = newFoodToken;
    }

    private ObstacleToken createObstacle() {
        ObstacleToken obstacleToken = new ObstacleToken();

        for (SnakeToken snakeToken : this.snake.getSnakeTokens()) {
            if (obstacleToken.intersects(snakeToken)) {
                return createObstacle(); // Recursively create a new obstacle if it intersects with the snake
            }
        }

        return obstacleToken;
    }

    private void changeDifficulty(int change) {
        if (paused) {
            this.difficulty += change;
            if (this.difficulty < 1) {
                this.difficulty = 1; // Ensure difficulty does not go below 1
            }

            difficultyLabel.setText(this.difficulty + "");
        }

    }

    private void gameOver() {
        paused = true;

        gameWindow.getChildren().add(newGameLabel);

        updateHighscore();
    }

    private void fillHighscoreList() {
        String highscoreList = "";

        for (String[] entry : GuiController.getInstance().getHighscores()) {
            highscoreList += entry[0] + ": " + entry[1] + "\n";
        }
        highscoreLabel.setText(highscoreList);
    }

    private void updateHighscore(){
        List <String[]> highscores = GuiController.getInstance().getHighscores();

        if (this.score > 0) {
            for (int i = 0; i < highscores.size(); i++) {
                if (this.score > Integer.parseInt(highscores.get(i)[1])) {
                    GuiController.getInstance().setNewHighscore((int) Math.floor(this.score));
                    GuiController.getInstance().loadPlayerNameInputView();
                }
            }
        }
    }
}