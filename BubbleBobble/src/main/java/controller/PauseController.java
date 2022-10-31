package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import launcher.App;
import utility.SoundEffect;
import view.GameView;
import java.io.IOException;

/**
 * PauseController class is for pause screen during the game
 */
public class PauseController {
    @FXML
    Button resumeButton;
    @FXML
    AnchorPane pausePane;
    private static MediaPlayer mediaPlayer;

    /**
     * Handle the event when the user click the resume button
     * @param mouseEvent MouseEvent
     */
    public void resumeClick(MouseEvent mouseEvent) {
        GameController.root.getChildren().remove(pausePane); //make the pause screen disappear
        GameController.getAnimationTimer().start(); //continue the animation timer. Continue the game
    }

    /**
     * Handle the event when the user click the restart button
     * @param mouseEvent MouseEvent
     */
    public void restartClick(MouseEvent mouseEvent) throws IOException {
        GameController.root.getChildren().remove(pausePane);
        Stage stage = (Stage) GameController.root.getScene().getWindow(); //
        GameView gameView = GameView.getGameView();
        String playerName = GameController.getTotalScore().getName();

        GameController.getMediaPlayer().stop();
        SoundEffect.setMute(false);
        GameController world = new GameController(gameView, playerName);

    }
    /**
     * Handle the event when the user click the mute button
     * @param mouseEvent MouseEvent
     */
    public void muteClick(MouseEvent mouseEvent) {
        mediaPlayer = GameController.getMediaPlayer(); //get the media player for the background music
        mediaPlayer.setMute(!mediaPlayer.isMute()); //set the opposite of the current boolean
        SoundEffect.setMute(!SoundEffect.getIsMute());
    }
    /**
     * Handle when the user click the exit button
     * @param mouseEvent the click mouse event
     * @throws IOException
     */
    public void exitClick(MouseEvent mouseEvent) throws IOException {
        App.setRoot("startscreen");
        GameController.getMediaPlayer().stop(); //stop the background music when exit
        SoundEffect.setMute(true); //make sure when you exit the sound effect is mute

    }

}
