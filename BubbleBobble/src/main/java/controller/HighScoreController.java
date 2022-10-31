package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import launcher.App;
import model.HighScore;
import utility.SoundEffect;
import view.GameView;
import java.io.IOException;


/**
 * HighController class is for the control of the high score board(Game Over)<br>
 * The only difference between a highscore board and a win screen is high score board show when you lose<br>
 * Win screen is when you win but also shows the history five highest records.
 */
public class HighScoreController {

    @FXML
    private static ImageView winImage;
    @FXML
    private static ImageView gameover;
    @FXML Text score1;
    @FXML Text score2;
    @FXML Text score3;
    @FXML Text score4;
    @FXML Text score5;
    @FXML Text score6;
    @FXML Text name1;
    @FXML Text name2;
    @FXML Text name3;
    @FXML Text name4;
    @FXML Text name5;
    @FXML Text name6;


    /**
     * Function for initialize the fxml. Set the name and score according to the highscore file.<br>
     * The last score and name is the current player name and score.
     */
    @FXML
    public void initialize() {
        HighScore[] highScores = HighScore.getHighScores(); //
        score1.setText(Integer.toString(highScores[0].getScore()));
        name1.setText(highScores[0].getName());
        score2.setText(Integer.toString(highScores[1].getScore()));
        name2.setText(highScores[1].getName());
        score3.setText(Integer.toString(highScores[2].getScore()));
        name3.setText(highScores[2].getName());
        score4.setText(Integer.toString(highScores[3].getScore()));
        name4.setText(highScores[3].getName());
        score5.setText(Integer.toString(highScores[4].getScore()));
        name5.setText(highScores[4].getName());

        score6.setText(Integer.toString(GameController.getTotalScore().getScore()));
        name6.setText(GameController.getTotalScore().getName());



    }
    /**
     * Handle when the player click restart button.
     * @param mouseEvent the click mouse event
     * @throws IOException
     */
    public void RestartClick(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow(); //get the stage
        GameView gameView = GameView.getGameView();
        String playerName = GameController.getTotalScore().getName(); //when restart, the player name is still the same
        SoundEffect.setMute(false);
        GameController.getMediaPlayer().stop();
        GameController world = new GameController(gameView, playerName); //start a new a game


    }
    /**
     * Handle when the player click menu button.
     * @param mouseEvent the click mouse event
     * @throws IOException
     */
    public void MenuClick(MouseEvent mouseEvent) throws IOException {
        App.setRoot("startscreen");//go to menu
        GameController.getMediaPlayer().stop(); //when go back to the menu no game background music
        SoundEffect.setMute(true);//make sure when you exit the sound effect is mute


    }


}
