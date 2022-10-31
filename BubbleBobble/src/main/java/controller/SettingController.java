package controller;

import controller.state.DifficultyState;
import controller.state.EasyState;
import controller.state.HardState;
import controller.state.MediumState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import launcher.App;

import java.io.IOException;

/**
 * SettingController class is for the setting event control
 */
public class SettingController {
    @FXML
    private ChoiceBox<String> difficultyBox;
    @FXML
    private ChoiceBox<String> wallBox;
    @FXML
    private ChoiceBox<String> backgroundBox;

    public static String wallChoice = "Pink";
    public static String difficultyChoice = "Easy";
    public static String backgroundChoice = "background";

    private String[] wallColor = {"Pink","Yellow","Brown","Purple","Orange","Green","SpecialGreen","Blue","Gray"};
    private String[] difficulty = {"Easy","Medium","Hard"};
    private String[] background = {"background","background2"};

    /**
     * Function for initialize the fxml
     */
    @FXML
    public void initialize() {
        difficultyBox.setValue(difficultyChoice);
        difficultyBox.getItems().addAll(difficulty);
        wallBox.setValue(wallChoice);
        wallBox.getItems().addAll(wallColor);
        backgroundBox.setValue(backgroundChoice);
        backgroundBox.getItems().addAll(background);

        difficultyBox.setOnAction(this::getDifficulty);
        wallBox.setOnAction(this::getWallColor);
        backgroundBox.setOnAction(this::getBackground);
    }

    /**
     * Get the difficulty of the users have chosen
     * @param event ActionEvent
     */
    public void getDifficulty(ActionEvent event) {
        difficultyChoice = difficultyBox.getValue();

        System.out.println(difficultyChoice);
    }
    /**
     * Get the wall color of the users have chosen
     * @param event ActionEvent
     */
    public void getWallColor(ActionEvent event) {
        wallChoice = wallBox.getValue();

        System.out.println(wallChoice);
    }

    /**
     * Get the background of the users have chosen
     * @param event ActionEvent
     */
    public void getBackground(ActionEvent event) {
        backgroundChoice = backgroundBox.getValue();

        System.out.println(backgroundChoice);
    }
    /**
     * Return a difficulty state according to what user have selected <br>
     * The difficulty state is for the game controller to know the difficulty of the game
     * @return difficulty state
     */
    public static DifficultyState getDifficulty(){
        if (difficultyChoice=="Easy"){
            return new EasyState();
        }
        else if (difficultyChoice=="Medium"){
            return new MediumState();
        }
        else if (difficultyChoice=="Hard"){
            return new HardState();
        }
        return null;
    }

    /**
     * Handle when the user click the back button
     * @param mouseEvent the click mouse event
     * @throws IOException file path does not exist.
     */
    public void backClick(MouseEvent mouseEvent) throws IOException {
        App.setRoot("startscreen"); //switch to start screen
    }
}
