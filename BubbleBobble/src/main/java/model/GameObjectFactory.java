package model;
import controller.GameController;
import controller.SettingController;
import javafx.scene.image.ImageView;
import launcher.App;
import model.enemy.BossEnemy;
import model.enemy.Enemy;
import model.enemy.NotShootEnemy;
import model.enemy.ShootEnemy;
import model.fruit.BubbleFruit;
import model.fruit.Fruit;
import model.fruit.InvincibleFruit;
import model.fruit.LifeFruit;
import model.fruitStrategy.AddLifeBehavior;
import model.fruitStrategy.BubbleBehavior;
import model.fruitStrategy.InvincibleBehavior;
import model.fruitStrategy.PowerUpBehavior;
import model.heroState.Hero;
import model.staticUnit.CeilingUnit;
import model.staticUnit.FloorUnit;
import model.staticUnit.WallUnit;

/**
 * GameObjectFactory is for generating different object according to different signs on the maps.<br>
 * This class implements the Factory method design pattern.
 */
public class GameObjectFactory {
    private int powerUpFruitCount = 1; // the maximum number of the power up fruit on each level

    /**
     * This function is for reset the count for power fruit since only one power up fruit for each level. <br>
     * When a power up fruit is generated on that level count will <br>
     * minus 1.Reset to 1 when switch to another level<br>
     */
    public void setCountOne(){ //only use when reload the map
        if (powerUpFruitCount==0){
            powerUpFruitCount = 1;
        }
    }
    /**
     * This function generate the game object according to the signs on the maps.
     */
    public GameObject getGameObject(char objectType, GameController gameController, int col, int row){
        String wallColor = SettingController.wallChoice;
        if(objectType=='*'){
            ImageView floorImageView = new ImageView(wallColor+".png");
            FloorUnit floorUnit = new FloorUnit(gameController, col, row, floorImageView);
            gameController.addFloorUnit(floorUnit);
            return floorUnit;

        }
        else if(objectType=='H'){
            ImageView heroImageView = new ImageView("HeroLeft.png");
            Hero hero = new Hero(gameController, col, row,heroImageView);
            gameController.addHero(hero);
            return hero;
        }
        else if(objectType=='|'){
            ImageView wallImageView = new ImageView(wallColor+".png");
            WallUnit wallUnit = new WallUnit(gameController, col, row, wallImageView);
            gameController.addWallUnit(wallUnit);
            return wallUnit;
        }
        else if(objectType=='_') {
            ImageView ceilingImageView = new ImageView(wallColor+".png");
            CeilingUnit ceilingUnit = new CeilingUnit(gameController, col, row, ceilingImageView);
            gameController.addCeilingUnit(ceilingUnit);
            return ceilingUnit;
        }
        else if(objectType=='M') {
            ImageView enemyImageView = new ImageView("MonsterLeft.png");
            Enemy enemy = new NotShootEnemy(gameController,col,row,enemyImageView);
            gameController.addEnemy(enemy);
            return enemy;
        }
        else if(objectType=='B') {
            ImageView enemyImageView = new ImageView("BossLeft.png");
            Enemy boss = new BossEnemy(gameController,col,row,enemyImageView);
            System.out.println(boss.width);
            System.out.println(boss.height);

            gameController.addEnemy(boss);
            return boss;
        }
        else if(objectType=='S'){
            ImageView enemyImageView = new ImageView("ShootMonsterLeft.png");
            Enemy shootEnemy = new ShootEnemy(gameController,col,row,enemyImageView);
            System.out.println(shootEnemy.width);
            System.out.println(shootEnemy.height);

            gameController.addEnemy(shootEnemy);
            return shootEnemy;

        }
        //for randomly setting the position of the power fruit in each level. One power fruit only.
        //generating the power up fruit
        else if (objectType=='-' && powerUpFruitCount==1){
            double rand;
            rand = Math.random();
            if (rand<0.05){ //5% will come out the fruit
                int min = 1;
                int max = 3;
                int range = max - min + 1; //range from 1 to three
                powerUpFruitCount-=1;

                rand = (int) (Math.random() * range) + min;

                if (rand==1) {
                    ImageView fruitImageView = new ImageView("BubbleBehavior.png"); //her
                    PowerUpBehavior powerUpBehavior = new BubbleBehavior();

                    Fruit bubbleFruit = new BubbleFruit(col * App.UNIT_SIZE, row * App.UNIT_SIZE, gameController, fruitImageView, powerUpBehavior, 20);
                    gameController.addFruit(bubbleFruit);
                    return bubbleFruit;
                }
                else if (rand==2){
                    ImageView fruitImageView = new ImageView("InvincibleBehavior.png"); //hero is invincible for 4 seconds
                    PowerUpBehavior powerUpBehavior = new InvincibleBehavior();

                    Fruit invincibleFruit = new InvincibleFruit(col* App.UNIT_SIZE,row* App.UNIT_SIZE, gameController, fruitImageView,powerUpBehavior, 20);
                    gameController.addFruit(invincibleFruit);
                    return invincibleFruit;
                }
                else if (rand==3){
                    ImageView fruitImageView = new ImageView("heart.png"); //ad one life for the hero
                    PowerUpBehavior powerUpBehavior = new AddLifeBehavior();

                    Fruit lifeFruit = new LifeFruit(col* App.UNIT_SIZE,row* App.UNIT_SIZE, gameController, fruitImageView,powerUpBehavior, 20);
                    gameController.addFruit(lifeFruit);
                    return lifeFruit;
                }

            }

        }
        return null;
    }
}
