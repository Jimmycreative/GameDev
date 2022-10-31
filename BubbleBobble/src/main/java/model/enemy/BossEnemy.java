package model.enemy;

import controller.BossProgressController;
import controller.GameController;
import javafx.scene.image.ImageView;
import model.projectile.BossProjectile;
import model.projectile.EnemyProjectile;
import model.staticUnit.StaticObject;
import model.heroState.Hero;
import model.heroState.ShieldState;

/**
 * One type of enemy. Boss won't become bubble when collide with hero projectile, it will <br>
 * decrease 0.1 of the boss life. Boss enemy can shoot the enemy projectile and the boss projectile.
 */
public class BossEnemy extends Enemy{
    private static final int SIZE = 60; //size of the image
    private static final double SHOOT_PROJECTILE_CHANCE = 0.01;//the chance to shoot a projectile
    private static final double SUPER_POWER = 0.003; //the chance for boss to use super power
    private GameController gameController;
    private BossProgressController bossProgressController;

    /**
     * The enemy for the hard difficulty of the game
     * @param gameController  The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     * @param bossImageView   The image view of the boss
     */
    public BossEnemy(GameController gameController, int colNum, int rowNum, ImageView bossImageView) {
        super(gameController, colNum, rowNum, bossImageView, SIZE);
        isBubbled = false;
        this.gameController = gameController;
        this.bossProgressController = gameController.bossProgressController;
    }

    /**
     * This function handles the chance of the change movement.
     */
    @Override
    public void updateEnemy() {
        //updates enemy, handling movement
        if (Math.random() < CHANGE_MOVEMENT_CHANCE) {
            jump();
        }
        if (Math.random() < CHANGE_MOVEMENT_CHANCE) {
            reverseDirection();
        }
    }
    /**
     * This function handles the boss shoot the enemy projectile and the boss projectile.
     */
    @Override
    public void shootProjectile() {

         // means both shoot and super power shoot behavior
            if (Math.random() < SUPER_POWER) { //the chance of using super power
                gameController.addBossProjectile(new BossProjectile(gameController, x, y, direction,new ImageView("SpecialAbility.png")));
            }
            if (Math.random() < SHOOT_PROJECTILE_CHANCE) { //the chance of shoot
                gameController.addEnemyProjectile(new EnemyProjectile(gameController, x, y, direction,new ImageView("enemyProjectile.png")));
            }

    }

    /**
     * This function handles the boss collides with hero projectile
     */
    public void collideWithProjectile() {
        //handles what to do if hit with a projectile by the hero
        //boss decrease the life
        bossProgressController.decreaseProgress();
    }

    /**
     * This function handles the boss collides with hero
     * @param hero
     */
    public void collideWith(Hero hero) {
        //handles collision with hero and what to do
        if (this.overlaps(hero)) {
            //if boss life is not zero
            hero.collideWithMook();
            if ((hero.getHeroState() instanceof ShieldState) && !turningAwayFromShield) {
                turningAwayFromShield = true;
                reverseDirection();
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
     * Nothing happens when collides with wall ceiling and floor
     * @param staticObject
     */
    @Override
    public void collideWith(StaticObject staticObject){
        //do nothing
    }

}
