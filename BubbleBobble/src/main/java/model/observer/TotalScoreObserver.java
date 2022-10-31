package model.observer;

import controller.GameController;
import javafx.scene.text.Text;
import model.GameObject;
import model.HighScore;

/**
 * The observer for updating the total score in the top right corner of the screen.
 */
public class TotalScoreObserver implements Observer{
    GameController gameController;
    GameObject gameObject;
    /**
     * TotalScoreObserver constructor
     * @param gameObject
     * @param gameController
     */
    public TotalScoreObserver(GameObject gameObject, GameController gameController){
        this.gameController = gameController;
        this.gameObject = gameObject;
    }
    /**
     * Update the total score text in the top right corner of the screen. Check whether the parameter text is increase or decrease.
     * @param score
     */
    @Override
    public void update(String score) {
        Text totalScoreText = gameController.getTotalScoreText();
        HighScore totalScore = gameController.getTotalScore();//the total score user get currently
        if (score.charAt(0) == '-') {

            int minusScore = Integer.parseInt(score.substring(1));
            totalScore.setScore(totalScore.getScore()-minusScore);
            totalScoreText.setText("Score: "+totalScore.getScore());
        }
        else if (score.charAt(0) == '+'){
            int addScore = Integer.parseInt(score.substring(1));
            totalScore.setScore(totalScore.getScore()+addScore);
            totalScoreText.setText("Score: "+totalScore.getScore());
            
        }


    }
}
