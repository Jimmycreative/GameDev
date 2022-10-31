package launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * App creates a stage and a scene. If want to modify the scene. setRoot can be used to load different fxml.<br>
 * The size of the scene is defined here.
 */
public class App extends Application {
    public static final int UNIT_SIZE = 20;
    public static final int WIDTH = 40;
    public static final int HEIGHT = 34;
    private static Scene scene;

//    public static void main(String[] args) { //move to the Main class to prevent complicated configuration problem.
//        launch(args);                       // If launch here, you will have to add vm option.
//    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/"+fxml+".fxml"));

        return fxmlLoader.load();
    }
    public static Scene getScene(){
        return scene;
    }
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/startscreen.fxml"));
        scene = new Scene(fxmlLoader.load(), WIDTH*UNIT_SIZE, HEIGHT*UNIT_SIZE);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}