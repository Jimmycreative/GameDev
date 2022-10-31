package model;

import controller.GameController;
import javafx.scene.image.ImageView;
import model.enemy.BossEnemy;
import model.enemy.Enemy;

/**
 * The Bubble class handles everything with the Hero's special ability, named the bubble.<br>
 * It begins at the hero, and grows covering the whole screen.<br>
 * Once it collides with an enemy, that enemy is bubbled.<br>
 * It is a dynamic object.
 */
public class Bubble extends DynamicObject {
    private int accel;
    private boolean isActive;

    /**
     * Bubble Constructor
     * @param gameController The main controller of the game
     * @param x              The x coordinate of the bubble
     * @param y              The y coordinate of the bubble
     */
    public Bubble(GameController gameController, int x, int y) {
        super(x, y, 0, gameController);
        accel = 1;
        isActive = true;
    }

    /**
     * Update the x,y and width height and acceleration of the bubble
     */
    @Override
    public void update() {
        if (width >= 2500) {
            markToRemove();
        }
        x -= accel / 2;
        y -= accel / 2;
        width += accel;
        height += accel;
        accel += 1;
    }

    @Override
    public ImageView getImageView() {
        return null;
    }

    @Override
    public void setImageView(ImageView imageView) {

    }

    @Override
    public void collideWithFloor() {
        // Nothing happens
    }

    @Override
    public void collideWithCeiling() {
        // Nothing happens
    }

    @Override
    public void collideWithWall() {
        // Nothing happens
    }

    /**
     * Handle when bubble collides with enemies. If the enemy is boss it will only decrease 0.1 of its life but if it is not shoot enemies<br>
     * or shoot enemies they will become bubble.
     * @param enemy
     */
    public void collideWith(Enemy enemy) {
        if (this.overlaps(enemy) && isActive==true) {
            if (enemy instanceof BossEnemy ==false){  //bubble collide with enemy will become bubble
                enemy.collideWithProjectile();

            }
            else if (enemy instanceof BossEnemy==true){ //bubble collide with boss enemy won't make the boss bubble
                enemy.collideWithProjectile();
                isActive = false;
            }

        }
    }

}

