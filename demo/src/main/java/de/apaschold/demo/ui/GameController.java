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

/**
 * <h2>GameController class</h2>
 * <li>Manages the game state, including the {@link Snake}, {@link FoodToken}, {@link ObstacleToken}, score, and difficulty</li>
 * <li>Handles user input for controlling the snake and game state (pause, new game, etc.)</li>
 * <li>Implements the main game loop and mechanics</li>
 */
public class GameController implements Initializable {

    //TODO variable size

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
    /** <h2>initialize method</h2>
     * <li>Initializes the game state and starts the game loop</li>
     * <li>Game loop is a {@link Timeline} animation</li>

     */
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
    /** <h2>keyboardControl method</h2>
     * <li>Handles keyboard input for controlling the snake and game state</li>
     * <li>Key bindings:</li>
     * <ul>
     *     <li>W, A, S, D: Move the snake up, left, down, right</li>
     *     <li>P: Pause/Unpause the game</li>
     *     <li>+: Increase difficulty (only when paused)</li>
     *     <li>-: Decrease difficulty (only when paused)</li>
     *     <li>N: Start a new game (only when paused)</li>
     *     <li>ESC: Exit the game</li>
     * </ul>
     * @param keyCode The {@link KeyCode} of the pressed key
     */

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

            default -> System.out.println("Key not recognized: " + keyCode);
        }
    }

    /** <h2>startNewGame method</h2>
     * <li>Starts a new game if the game is currently paused</li>
     * <li>Resets the snake, obstacles, food token, and score</li>
     * <li>Generates obstacles based on the current difficulty level</li>
     */

    private void startNewGame() {
        if (paused) {
            this.snake = new Snake();

            this.obstacles.clear();
            for (int i = 0; i < this.difficulty * 6; i++) {
                obstacles.add(createObstacle());
            }

            createNewFoodToken();

            this.score = 0;

            paused = !paused;
        }
    }

    //5. game mechanics
    /** <h2>gameMechanics method</h2>
     * <li>Handles the main game mechanics, including moving the snake, checking for collisions, and updating the game state</li>
     * <li>If the snake collides with a wall or itself, the game ends</li>
     * <li>If the snake eats a food token, a new food token is created and the score is increased based on the difficulty level</li>
     * <li>If the game is not paused, the game window is updated to reflect the current state</li>
     */

    private void gameMechanics(){
        // Here you would implement the game mechanics, such as moving the snake,
        // checking for collisions, and updating the game states
        this.snake.move();

        if(this.snake.checkCollisionWithWallsOrItself(this.obstacles)){
            gameOver();
            return; // Exit the method to stop further processing
        }

        if(this.snake.eat(this.foodToken)){
            createNewFoodToken();
            this.score += 1 + this.difficulty * DIFFICULTY_MULTIPLIER; // Increase score based on difficulty
        }

        if(!paused){
            updateGameWindow();
        }
    }

    /** <h2>updateGameWindow method</h2>
     * <li>Updates the game window to reflect the current state of the game</li>
     * <li>Clears the game window and updates the {@link Token}</li>
     * <li>Updates the score label to show the current score</li>
     */
    private void updateGameWindow() {
        gameWindow.getChildren().clear();
        gameWindow.getChildren().add(this.foodToken.getShape());
        gameWindow.getChildren().addAll(this.snake.getSnakeShape());

        for (ObstacleToken obstacle : this.obstacles) {
            gameWindow.getChildren().add(obstacle.getShape());
        }

        scoreLabel.setText((int) Math.floor(this.score) + "");
    }

    /** <h2>createNewFoodToken method</h2>
     * <li>Creates a new {@link FoodToken} ensuring it does not intersect with the snake or obstacles</li>
     * <li>If an intersection is detected, the method is called recursively to create a new token</li>
     */
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

    /** <h2>createObstacle method</h2>
     * <li>Creates a new {@link ObstacleToken} ensuring it does not intersect with the snake</li>
     * <li>If an intersection is detected, the method is called recursively to create a new token</li>
     * @return A new ObstacleToken that does not intersect with the snake
     */
    private ObstacleToken createObstacle() {
        ObstacleToken obstacleToken = new ObstacleToken();

        for (SnakeToken snakeToken : this.snake.getSnakeTokens()) {
            if (obstacleToken.intersects(snakeToken)) {
                return createObstacle(); // Recursively create a new obstacle if it intersects with the snake
            }
        }

        return obstacleToken;
    }

    /** <h2>changeDifficulty method</h2>
     * <li>Changes the game difficulty by the specified amount if the game is paused</li>
     * <li>Ensures the difficulty does not go below 1</li>
     * <li>Updates the difficulty label to show the current difficulty</li>
     * @param change The amount to change the difficulty by (positive or negative)
     */
    private void changeDifficulty(int change) {
        if (paused) {
            this.difficulty += change;
            if (this.difficulty < 1) {
                this.difficulty = 1; // Ensure difficulty does not go below 1
            }

            difficultyLabel.setText(this.difficulty + "");
        }

    }

    /** <h2>gameOver method</h2>
     * <li>Handles the game over state</li>
     * <li>Pauses the game and displays the new game label</li>
     * <li>Updates the highscore list if the current score is a new highscore</li>
     */
    private void gameOver() {
        paused = true;

        gameWindow.getChildren().add(newGameLabel);

        updateHighscore();
    }

    /** <h2>fillHighscoreList method</h2>
     * <li>Fills the highscore label with the current highscores from the {@link GuiController}</li>
     */
    private void fillHighscoreList() {
        String highscoreList = "";

        for (String[] entry : GuiController.getInstance().getHighscores()) {
            highscoreList += entry[0] + ": " + entry[1] + "\n";
        }
        highscoreLabel.setText(highscoreList);
    }

    /** <h2>updateHighscore method</h2>
     * <li>Checks if the current score is a new highscore and updates the highscore list if so</li>
     * <li>If a new highscore is achieved, prompts the player to enter their name</li>
     */
    private void updateHighscore(){
        List <String[]> highscores = GuiController.getInstance().getHighscores();

        if (this.score > 0) {
            for (String[] highscore : highscores) {
                if (this.score > Integer.parseInt(highscore[1])) {
                    GuiController.getInstance().setNewHighscore((int) Math.floor(this.score));
                    GuiController.getInstance().loadPlayerNameInputView();
                }
            }
        }
    }
}