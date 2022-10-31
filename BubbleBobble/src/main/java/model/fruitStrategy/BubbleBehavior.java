package model.fruitStrategy;

import controller.GameController;
import model.fruitStrategy.PowerUpBehavior;

/**
 * The power up behavior for the bubble fruit
 */
public class BubbleBehavior implements PowerUpBehavior {
    /**
     * Power up let the player use the key code w. bubble special ability.
     */
    @Override
    public void powerUp(GameController world) {
        world.getHero().setChargeToReady();
    }
}
