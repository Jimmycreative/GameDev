package controller.state;

import controller.GameController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.enemy.Enemy;
import model.fruit.Fruit;
import model.fruit.NormalFruit;
import model.fruitStrategy.NormalBehavior;
import model.fruitStrategy.PowerUpBehavior;

/**
 * Hard difficulty. Only one level with a boss.<br>
 * Progress bar is its life. HeroProjectile and hero super power only damage 0.1 of the progress<br>
 * When hit 10 times boss will die.<br>
 * The fruit is treasure 5000 points.<br>
 * Also have power up fruit.
 */
public class HardState implements DifficultyState {
    String[] worldArray = {"worldBoss"};
    /**
     * Fall treasure 5000points
     * @param gameController
     * @param enemy
     */
    @Override
    public void fallFruit(AnchorPane root, GameController gameController, Enemy enemy) {
        ImageView fruitImageView = new ImageView("treasure.png");
        PowerUpBehavior powerUpBehavior = new NormalBehavior(); //no behavior
        Fruit fruit = new NormalFruit(enemy.x, enemy.y, gameController, fruitImageView, powerUpBehavior, 5000); //when enemy dies, fruit image is shown
        root.getChildren().add(fruitImageView);
        gameController.addFruit(fruit);
    }

    @Override
    public String[] getWorldArray() {
        return worldArray;
    }
}
