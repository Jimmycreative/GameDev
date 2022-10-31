package controller;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import launcher.App;
import java.io.IOException;

/**
 * startScreenController class is for the control of the start screen
 */
public class startScreenController {

    /**
     * Handle when the user click the start button
     * @param mouseEvent the click mouse event
     * @throws IOException
     */
    public void startClick(MouseEvent mouseEvent) throws IOException {
        App.setRoot("playerInput");
    }

    /**
     * Handle when the user click the info button
     * @param mouseEvent the click mouse event
     * @throws IOException
     */
    public void infoClick(MouseEvent mouseEvent) throws IOException {
        App.setRoot("info");
    }

    /**
     * Handle when the user click the settings button
     * @param mouseEvent the click mouse event
     * @throws IOException
     */
    public void settingsClick(MouseEvent mouseEvent) throws IOException {
        App.setRoot("settings");
    }
    /**
     * Handle when the user click the exit button
     * @param mouseEvent the click mouse event
     * @throws IOException
     */
    public void exitClick(MouseEvent mouseEvent) {
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
