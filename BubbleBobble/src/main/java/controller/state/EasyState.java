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
 * Easy difficulty. Only NotShootEnemy object in all the levels.<br>
 * The fruit is banana 70 points. Kill a enemy get 50 points.
 * Each level will fall a power up fruit.
 */
public class EasyState implements DifficultyState {
    String[] worldArray = {"world1","world2","world3"};

    /**
     * Fall banana 70 points
     * @param root
     * @param gameController
     * @param enemy
     */
    @Override
    public void fallFruit(AnchorPane root, GameController gameController, Enemy enemy) {
        ImageView fruitImageView = new ImageView("banana.png");
        PowerUpBehavior powerUpBehavior = new NormalBehavior(); //no behavior
        Fruit fruit = new NormalFruit(enemy.x, enemy.y, gameController, fruitImageView, powerUpBehavior, 70); //when enemy dies, fruit image is shown
        root.getChildren().add(fruitImageView);
        gameController.addFruit(fruit);

    }

    @Override
    public String[] getWorldArray() {
        return worldArray;
    }
}
