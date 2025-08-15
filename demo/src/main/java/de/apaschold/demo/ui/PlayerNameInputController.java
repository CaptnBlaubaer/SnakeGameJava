package de.apaschold.demo.ui;

import de.apaschold.demo.logic.fileHandling.CsvHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.List;

public class PlayerNameInputController {
    @FXML
    private TextField playerNameInput;

    @FXML
    private Label errorLabel;

    public void keyboardControl(KeyCode keyCode) {
        if (keyCode == KeyCode.ENTER) {
            String playerName = playerNameInput.getText().trim();

            if (!playerName.isEmpty()) {
                updateHighscores(playerName);
            } else {
                errorLabel.setVisible(true);
            }
        }
    }

    public void updateHighscores(String playerName) {
        List<String[]> highscores = GuiController.getInstance().getHighscores();
        int newHighscore = GuiController.getInstance().getNewHighscore();

        for (int i = 0; i < highscores.size(); i++) {
            if (newHighscore > Integer.parseInt(highscores.get(i)[1])) {
                highscores.add(i, new String[]{playerName, String.valueOf(newHighscore)});

                if(highscores.size() > GuiController.LENGTH_HIGHSCORES) {
                    highscores.remove(highscores.size() - 1);
                }
                break;
            }
        }
        CsvHandler.getInstance().writeHighscoreToCsv(highscores);

        GuiController.getInstance().loadGameView();
    }
}
