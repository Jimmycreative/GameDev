package model.fruitStrategy;

import controller.GameController;

/**
 * The power behavior for different types of fruit
 */
public interface PowerUpBehavior {
    /**
     * Power up
     * @param gameController
     */
    void powerUp(GameController gameController);
}
