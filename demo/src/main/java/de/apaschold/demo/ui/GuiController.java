package de.apaschold.demo.ui;

import de.apaschold.demo.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiController {
    public static GameController gameController;

    private final Stage stage;

    public GuiController(Stage stage){
        this.stage = stage;
    }

    public void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 500, 750);

        gameController = fxmlLoader.getController();

        scene.setOnKeyPressed(event -> {
            gameController.keyboardControl(event.getCode());
        });

        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
    }
}
