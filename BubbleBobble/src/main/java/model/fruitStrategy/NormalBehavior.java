package model.fruitStrategy;

import controller.GameController;

/**
 * The powerup behavior for the normal fruit.
 */
public class NormalBehavior implements PowerUpBehavior {
    /**
     * Normal fruit has nothing to do with the power up
     * @param gameController
     */
    @Override
    public void powerUp(GameController gameController) {
        //do nothing
    }
}
