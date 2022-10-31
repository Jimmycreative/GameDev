package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

/**
 * This is the controller which handles the boss life progress bar.<br>
 */
public class BossProgressController {
    @FXML
    ProgressBar progressBar;
    @FXML
    GridPane gridPane;
    double progress = 1;

    /**
     * Function initialize the progress bar to be red and set the coordinates of the prorgess bar.<br>
     * Two actions of two buttons are handled.
     */
    @FXML
    public void initialize(){

        progressBar.setStyle("-fx-accent: red;");
        gridPane.setLayoutX(260);
        gridPane.setLayoutY(70);
    }

    /**
     * This function is used to decrease the progress of the progress bar.<br>
     * Each time the boss attacked by the hero decrease 0.1 of its life.
     */
    public void decreaseProgress(){
        if (progress>0) {
            progress -= 0.1;
            progressBar.setProgress(progress);
        }

    }

}
