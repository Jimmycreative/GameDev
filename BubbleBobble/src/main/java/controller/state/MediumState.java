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
 * Medium difficulty. One ShootEnemy object in the second level.<br>
 * And 2 shoot enemies in the last level.<br>
 * The fruit is orange 150 points. Kill a enemy get 50 points.
 * Each level will fall a power up fruit.
 */
public class MediumState implements DifficultyState {
    String[] worldArray = {"world1","world2Medium","world3Medium"};
    /**
     * Fall orange 150 points
     * @param root
     * @param gameController
     * @param enemy
     */
    @Override
    public void fallFruit(AnchorPane root, GameController gameController, Enemy enemy) {
        ImageView fruitImageView = new ImageView("FruitOrange.png");
        PowerUpBehavior powerUpBehavior = new NormalBehavior(); //no behavior
        Fruit fruit = new NormalFruit(enemy.x, enemy.y, gameController, fruitImageView,powerUpBehavior, 150); //when enemy dies, fruit image is shown
        root.getChildren().add(fruitImageView);
        gameController.addFruit(fruit);
    }

    @Override
    public String[] getWorldArray() {
        return worldArray;
    }
}
