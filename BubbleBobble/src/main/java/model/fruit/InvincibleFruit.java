package model.fruit;

import controller.GameController;
import javafx.scene.image.ImageView;
import model.fruitStrategy.PowerUpBehavior;
/**
 * The type of fruit which has the invincible power up for the hero.
 */
public class InvincibleFruit extends Fruit {
    private final GameController gameController;
    private final PowerUpBehavior powerUpBehavior;

    /**
     * InvincibleFruit
     * @param x              The x coordinate of the fruit
     * @param y              The y coordinate of the fruit
     * @param gameController The main controller of the game
     * @param fruitImageView The imageView of the fruit
     * @param pointValue     The point of the fruit. Different types different points.
     */
    public InvincibleFruit(int x, int y, GameController gameController, ImageView fruitImageView, PowerUpBehavior powerUpBehavior, int pointValue) {
        super(x, y, gameController, fruitImageView, pointValue);
        this.gameController = gameController;
        this.powerUpBehavior = powerUpBehavior;
    }

    /**
     * The function for power up according to different fruit power up behavior
     */
    @Override
    public void powerUp() {
        powerUpBehavior.powerUp(gameController);
    }
}
