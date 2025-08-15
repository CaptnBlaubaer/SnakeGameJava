package de.apaschold.demo;

import de.apaschold.demo.ui.GuiController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GuiController.getInstance().setMainStage(stage);
        GuiController.getInstance().loadGameView();
    }

    public static void main(String[] args) {
        launch();
    }
}