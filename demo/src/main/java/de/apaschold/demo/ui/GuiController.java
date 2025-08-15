package de.apaschold.demo.ui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.logic.fileHandling.CsvHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GuiController {
    //0. constants
    public static final int LENGTH_HIGHSCORES = 5;

    //1. attributes
    private Stage stage;
    private List<String[]> highscores;
    private int newHighscore;
    public static GuiController instance;

    //2. constructors
    private GuiController(){
        this.highscores = CsvHandler.getInstance().readHighscoresFromCsv();
    }

    public static synchronized GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    //3. methods to load game view
    public void loadGameView(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 500, 750);

            GameController gameController = fxmlLoader.getController();
            scene.setOnKeyPressed(event -> {
                gameController.keyboardControl(event.getCode());
            });

            stage.setTitle("Snake");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerNameInputView(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("player-name-input-view.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 450, 200);

            PlayerNameInputController playerNameInputController = fxmlLoader.getController();
            scene.setOnKeyPressed(event -> {
                playerNameInputController.keyboardControl(event.getCode());
            });

            stage.setTitle("Snake - Enter Player Name");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //4. getters and setters
    public List<String[]> getHighscores() {
        return this.highscores;
    }

    public void setNewHighscore(int newHighscore) {
        this.newHighscore = newHighscore;
    }

    public int getNewHighscore() {
        return this.newHighscore;
    }

    public void setMainStage(Stage mainStage) {
        this.stage = mainStage;
    }
}
