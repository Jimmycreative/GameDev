package model.fruitStrategy;

import controller.GameController;
import model.heroState.InvincibleState;

/**
 * The power up behavior for the invincible fruit
 */
public class InvincibleBehavior implements PowerUpBehavior{
    /**
     * Power up. Chnage the hero state to invincible.
     * @param gameController
     */
    @Override
    public void powerUp(GameController gameController) {
        gameController.getHero().setHeroState(new InvincibleState(gameController.getHero()));
    }
}
