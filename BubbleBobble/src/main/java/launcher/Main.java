package launcher;

import javafx.application.Application;

/**
 * Main class for launching. Because if launch in the App, you will have to add vm option, and sometimes <br>
 * it's complicated. Launch here doesn't need to but probably have to add the framework support and choose maven.
 */
public class Main {
    public static void main(String[] args) {
        Application.launch(App.class);
    }
}
