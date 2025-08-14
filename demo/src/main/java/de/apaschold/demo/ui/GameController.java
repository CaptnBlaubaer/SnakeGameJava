package de.apaschold.demo.ui;

import de.apaschold.demo.logic.Direction;
import de.apaschold.demo.model.FoodToken;
import de.apaschold.demo.logic.Snake;
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

    //0. constants
    private static boolean paused = true;

    //1. attributes
    private Snake snake = new Snake();
    private FoodToken foodToken = new FoodToken();

    @FXML
    private Label welcomeText;

    @FXML
    private Pane gameWindow;

    //2. initializer
    @Override
    public void initialize(URL location,ResourceBundle resources) {
        gameWindow.getChildren().addAll(this.snake.getSnakeShape());
        gameWindow.getChildren().add(foodToken.getShape());

        Timeline runGame = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {

                if (!paused){
                    gameMechanics();
                    updateGameWindow();
                }
            }
        }));
        runGame.setCycleCount(Timeline.INDEFINITE);
        runGame.play();
    }

    // 4. methods
    private void gameMechanics(){
        // Here you would implement the game mechanics, such as moving the snake,
        // checking for collisions, and updating the game state.
        System.out.println("Game mechanics running...");
        this.snake.move();

    }

    private void updateGameWindow() {
        gameWindow.getChildren().clear();
        gameWindow.getChildren().add(this.foodToken.getShape());
        gameWindow.getChildren().addAll(this.snake.getSnakeShape());
    }

    public void keyboardControl(KeyCode keyCode) {
        System.out.println(keyCode);

        switch (keyCode) {
            case P -> paused = !paused;
            case W -> this.snake.setDirection(Direction.UP);
            case A -> this.snake.setDirection(Direction.LEFT);
            case S -> this.snake.setDirection(Direction.DOWN);
            case D -> this.snake.setDirection(Direction.RIGHT);

            default -> System.out.println("Key not recognized");
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}