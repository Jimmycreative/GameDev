package model.enemy;

import controller.GameController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.projectile.EnemyProjectile;
import model.GameObject;
import model.staticUnit.StaticObject;
import model.heroState.Hero;
import model.heroState.ShieldState;
import utility.SoundEffect;

/**
 * The enemy that can shoot
 */
public class ShootEnemy extends Enemy {
    private static final int SIZE = 40;//the size of the enemy
    private static final double SHOOT_PROJECTILE_CHANCE = 0.01;
    private ImageView enemyImageView;
    int bubbleTimer; //the time enemy get stuck in the bubble
    private GameController gameController;

    /**
     * The enemy for the medium difficulty of the game
     * @param gameController  The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     * @param enemyImageView  The image view of the shoot enemy
     */
    public ShootEnemy(GameController gameController, int colNum, int rowNum, ImageView enemyImageView) {
        super(gameController, colNum, rowNum, enemyImageView, SIZE);
        this.enemyImageView = enemyImageView;
        this.gameController = gameController;
        isBubbled = false;
        bubbleTimer = BUBBLED_FRAMES;//300 (5seconds)
    }
    /**
     * Handle when the enemy is bubbled and change the movement of the enemy.
     */
    @Override
    public void updateEnemy() {
        //updates enemy, handling movement

        if (isBubbled) {
            bubbleTimer -= 1;
            if (bubbleTimer <= 0) {
                isBubbled = false;
                bubbleTimer = BUBBLED_FRAMES;
                xAccel = 1.5;
                direction = 1;
                if (Math.random() < 0.5) {
                    reverseDirection();
                }

                yAccel = GameObject.GRAVITY;
                Image original = new Image("ShootMonsterLeft.png"); //replace back to the monster image
                enemyImageView.setImage(original);
            }

        } else {

            if (Math.random() < CHANGE_MOVEMENT_CHANCE) {
                jump();
            }
            if (Math.random() < CHANGE_MOVEMENT_CHANCE) {
                reverseDirection();

            }
        }
    }
    /**
     * Handle when colliding with the projectile
     */
    public void collideWithProjectile() {
        //handles what to do if hit with a projectile by the hero
        Image enemyInBubble = new Image("enemyInBubble2.png");
        enemyImageView.setImage(enemyInBubble);

        if (!isBubbled) {
            SoundEffect.BUBBLED.setToLoud();
            SoundEffect.BUBBLED.play();
            isBubbled = true;
            yVelocity = 0;
            xAccel = 0;
            yAccel = -0.1;
        }
    }
    /**
     * Handle when colliding with hero
     * @param hero
     */
    public void collideWith(Hero hero) {
        //handles collision with hero and what to do
        if (this.overlaps(hero)) {
            if (!isBubbled) {
                hero.collideWithMook();
                if ((hero.getHeroState() instanceof ShieldState) && !turningAwayFromShield) {//if hero is shield state won't die and not turning away from shield
                    turningAwayFromShield = true;
                    reverseDirection();
                }
            }

            else if (!canRemove){
                SoundEffect.POP.play();
                die();
            }
        }
        if (turningAwayFromShield) {
            if (turningAwayCount <= 0) {
                turningAwayCount = 10;
                turningAwayFromShield = false;
            }
            turningAwayCount -= 1;
        }
    }
    /**
     * The enemy can shoot
     */
    public void shootProjectile() {

            if (Math.random() < SHOOT_PROJECTILE_CHANCE && isBubbled==false) { //the chance of shoot
                gameController.addEnemyProjectile(new EnemyProjectile(gameController, x, y, direction,new ImageView("enemyProjectile.png")));
            }

    }
    /**
     * Hanlde when colliding with wall ceiling floor.
     * @param staticObject
     */
    @Override
    public void collideWith(StaticObject staticObject){
        if (this.overlaps(staticObject)) {
            if (isBubbled) {
                yVelocity = 0;
                yAccel = 0;
            }
        }
    }


}
