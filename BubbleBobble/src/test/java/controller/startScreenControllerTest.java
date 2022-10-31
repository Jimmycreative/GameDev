package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import launcher.App;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(ApplicationExtension.class)
class startScreenControllerTest extends ApplicationTest {
    @Start
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/startscreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 680);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    /**
     * Check start button click.
     */
    @Test
    void startClickTest(FxRobot robot) {
        robot.doubleClickOn("#startButton");
    }

    /**
     * Check info button click.
     */
    @Test
    void infoClickTest(FxRobot robot) {
        robot.doubleClickOn("#infoButton");
    }

    /**
     * Check settings click.
     */
    @Test
    void settingsClickTest(FxRobot robot) {
        robot.doubleClickOn("#settingsButton");
    }
    /**
     * Check exit click.
     */
    @Test
    void exitClickTest(FxRobot robot) {
        robot.doubleClickOn("#exitButton");
    }
}