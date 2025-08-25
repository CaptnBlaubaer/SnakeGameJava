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

    /** <h2>keyboardControl method</h2>
     * <li>Handles keyboard input for the player name input field</li>
     * <li>Submits the player name when the Enter key is pressed</li>
     * <li>Displays an error message if the input is empty</li>
     * @param keyCode The KeyCode of the pressed key
     */
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

    /** <h2>updateHighscores method</h2>
     * <li>Updates the highscores list with the new player name and score</li>
     * <li>Inserts the new highscore in the correct position and removes the lowest score if necessary</li>
     * <li>Saves the updated highscores to the CSV file and loads the game view</li>
     * @param playerName The name of the player to add to the highscores
     */
    public void updateHighscores(String playerName) {
        List<String[]> highscores = GuiController.getInstance().getHighscores();
        int newHighscore = GuiController.getInstance().getNewHighscore();

        for (int i = 0; i < highscores.size(); i++) {
            if (newHighscore > Integer.parseInt(highscores.get(i)[1])) {
                highscores.add(i, new String[]{playerName, String.valueOf(newHighscore)});

                if(highscores.size() > GuiController.LENGTH_HIGHSCORES) {
                    highscores.removeLast();
                }
                break;
            }
        }
        CsvHandler.getInstance().writeHighscoreToCsv(highscores);

        GuiController.getInstance().loadGameView();
    }
}
