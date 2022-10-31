package model.fruitStrategy;

import controller.GameController;

/**
 * The power up behavior for the life fruit
 */
public class AddLifeBehavior implements PowerUpBehavior{
    /**
     * Add one hero's life
     * @param gameController
     */
    @Override
    public void powerUp(GameController gameController) {
        gameController.addOneLive(); //add one hero live
        gameController.updateLiveText();
    }
}
