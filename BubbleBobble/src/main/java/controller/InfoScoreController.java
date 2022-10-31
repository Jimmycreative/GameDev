package controller;

import javafx.scene.input.MouseEvent;
import launcher.App;

import java.io.IOException;

/**
 * InfoController class is for the control of the Info page
 */
public class InfoScoreController {
    /**
     * Handle click back button
     * @param mouseEvent
     */
    public void backClick(MouseEvent mouseEvent) throws IOException {
        App.setRoot("startscreen"); //switch to start screen
    }
}
