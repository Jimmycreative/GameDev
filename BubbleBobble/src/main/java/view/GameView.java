package view;

import controller.SettingController;
import launcher.App;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


/**
 * GameView class handles the screen of the game.<br>
 * Initialize the game view with a background choose by the player
 */
public class GameView {

    private static GameView gameView;
    private GameView(){};

    /**
     * Return a game view since this class uses singleton pattern <br>
     * @return gameView
     */
    public static GameView getGameView() {
        if (gameView == null) {
            gameView = new GameView();
        }
        return gameView;
    }
    /**
     *Create the view of the game
     * @return root Anchorpane
     */
    public AnchorPane createView(String backgroundChoice){
        AnchorPane root = new AnchorPane();
        Image backgroundImage = new Image(backgroundChoice+".png", App.UNIT_SIZE* App.WIDTH, App.UNIT_SIZE* App.HEIGHT, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        root.setBackground(new Background(background));
        return root;
    }

}
