package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import launcher.App;
import utility.SoundEffect;
import view.GameView;

import java.io.IOException;

/**
 * This is the controller which handles the player input screen.<br>
 * Two actions of two buttons are handled.
 */
public class PlayerInputController {

    String playerInput;
    @FXML
    TextField userInput;

    /**
     * Handle when the user click the play button
     * @param mouseEvent the click mouse event
     * @throws IOException
     */
    public void playClick(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        GameView gameView = GameView.getGameView();
        SoundEffect.setMute(false); //initialize the sound to mute. Sound will open automatically when redirect to the game screen
        playerInput = userInput.getText(); //get the player name from what the user enter
        GameController world = new GameController(gameView, playerInput);

    }

    /**
     * Handle when the user click the back button
     * @param mouseEvent the click mouse event
     * @throws IOException
     */
    public void backClick(MouseEvent mouseEvent) throws IOException {
        App.setRoot("startscreen"); //back to menu
    }
}
