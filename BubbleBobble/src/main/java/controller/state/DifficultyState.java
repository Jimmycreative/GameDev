package controller.state;

import controller.GameController;
import javafx.scene.layout.AnchorPane;
import model.enemy.Enemy;

/**
 * This is the state of different difficulty of the game chosen by player.<br>
 */
public interface DifficultyState {
    /**
     * Different difficulty of game fall different points of fruit.
     * @param root
     * @param gameController
     * @param enemy
     */
    void fallFruit(AnchorPane root, GameController gameController, Enemy enemy);
    /**
     * Get the world array different difficulty of the game different array .<br>
     */
    String[] getWorldArray();
}
