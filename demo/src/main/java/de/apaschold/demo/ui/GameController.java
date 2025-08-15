package de.apaschold.demo.ui;

import de.apaschold.demo.logic.Direction;
import de.apaschold.demo.logic.fileHandling.CsvHandler;
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
import java.util.List;

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
    private static boolean PAUSED = true;
    private static final double SPEED = 0.1; // Speed of the game in seconds
    private static final int LENGTH_HIGHSCORES = 5;

    //1. attributes
    private Snake snake;
    private FoodToken foodToken;
    private double score = 0;
    private int difficulty = 0;
    private List<String[]> highscores;

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
        this.highscores = CsvHandler.getInstance().readHighscoresFromCsv();
        fillHighscoreList();

        Timeline runGame = new Timeline(new KeyFrame(Duration.seconds(SPEED), new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {

                if (!PAUSED){
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

        PAUSED = !PAUSED;
    }

    // 4. control method
    public void keyboardControl(KeyCode keyCode) {

        switch (keyCode) {
            case P -> PAUSED = !PAUSED;
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
        PAUSED = true;

        gameWindow.getChildren().add(newGameLabel);

        updateHighscore("Player");

        this.score = 0;
    }

    private void fillHighscoreList() {
        String highscoreList = "";

        for (String[] entry : this.highscores) {
            highscoreList += entry[0] + ": " + entry[1] + "\n";
        }
        highscoreLabel.setText(highscoreList);
    }

    private void updateHighscore(String playerName){
        if (this.score > 0) {

            if(this.score < Integer.parseInt(this.highscores.getLast()[1]) && this.highscores.size() < LENGTH_HIGHSCORES) {
                this.highscores.add(new String[]{playerName, String.valueOf((int) Math.floor(this.score))});
            } else {
                for (int i = 0; i <= this.highscores.size(); i++) {
                    if (this.score > Integer.parseInt(this.highscores.get(i)[1])) {
                        this.highscores.add(i, new String[]{playerName, String.valueOf((int) Math.floor(this.score))});
                        if (this.highscores.size() > LENGTH_HIGHSCORES) {
                            this.highscores.remove(LENGTH_HIGHSCORES); // Keep only the top 10 scores
                        }
                        break;
                    }
                }
            }

            CsvHandler.getInstance().writeHighscoreToCsv(this.highscores);

            fillHighscoreList();
        }
    }
}