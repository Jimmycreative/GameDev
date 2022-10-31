package model.observer;

import controller.GameController;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.GameObject;

import java.util.ArrayList;
/**
 * The observer for updating the temporary display score text on the screen.
 */
public class ScoreTextObserver implements Observer{
    GameController gameController;
    GameObject gameObject;
    /**
     * ScoreTextObserver constructor
     * @param gameObject
     * @param gameController
     */
    public ScoreTextObserver(GameObject gameObject, GameController gameController) {
        this.gameController = gameController;
        this.gameObject = gameObject;
    }

    /**
     * Display the temporary score on screen based on gameObject position.
     * @param score
     */
    @Override
    public void update(String score) {

        if (gameController.getReadyToReset()==true){
            gameController.root.getChildren().clear();
        }
        ArrayList<Text> scoreText = gameController.getScoreText(); //scoreText list is used to store all the text like +70 +50 -100 related to the score
        Text text = new Text(score); //ex: when enemy dies, score +50 is shown
        text.setX(gameObject.x);
        text.setY(gameObject.y);
        text.setFont(Font.font("verdana", FontWeight.BOLD, 20));
        text.setFill(Color.WHITE);
        scoreText.add(text);
        gameController.root.getChildren().add(text);//add the text on the pane
    }
}
