package model.projectile;

import controller.GameController;
import javafx.scene.image.ImageView;
import model.enemy.Enemy;
import model.heroState.Hero;

/**
 * The BossProjectile class handles the specificities with the projectile being shot from the boss.<br>
 * It also can hurt a hero. Boss projectile won't move, but it's really large. Hero shield can protect the attack from it.
 */
public class BossProjectile extends Projectile {
    private static final int SIZE = 300;

    private boolean isActive;
    private int activeFrames;
    private int timer;
    private ImageView bossProjectileImageView;

    /**
     * BossProjectile constructor
     * @param gameController The main controller of the game
     * @param x              The x coordinate of the boss projectile
     * @param y              The y coordinate of the boss projectile
     * @param direction      The direction of the boss projectile
     * @param bossProjectileImageView The image view of the boss projectile
     */
    public BossProjectile(GameController gameController, int x, int y, int direction, ImageView bossProjectileImageView) {
        super(x, y, SIZE, gameController);
        this.direction = direction;


        isActive = true;
        activeFrames = 60;
        timer = activeFrames;
        this.bossProjectileImageView = bossProjectileImageView;
    }


    /**
     * Won't update the velocity of the enemy projectile but will turn it into inactive in a time
     */
    @Override
    public void update() {


        if (timer < 0) {
            isActive = false;
        }

        if (timer < -100) {
            markToRemove();
        }
        timer -= 1;
    }

    /**
     * Get the boss projectile imageview
     * @return bossProjectileImageView
     */
    @Override
    public ImageView getImageView() {
        return bossProjectileImageView;
    }

    /**
     * Set the image view of the boss projectile
     * @param imageView
     */
    @Override
    public void setImageView(ImageView imageView) {
        bossProjectileImageView = imageView;
    }


    @Override
    public void collideWithFloor() {
        // Nothing happen
    }

    @Override
    public void collideWithCeiling() {
        // Nothing happen
    }

    @Override
    public void collideWithWall() {
        // Nothing happen
    }

    /**
     * Handle the collision with hero.
     * @param hero
     */
    @Override
    public void collideWith(Hero hero) {
        if(this.overlaps(hero) && isActive) {
            hero.collideWithBossProjectile(this);
        }
    }
    @Override
    public void collideWith(Enemy enemy) {
        //Nothing happens
    }
    public boolean getIsActive(){return isActive;}
    public void setIsActive(){
        isActive = !isActive; // set to the opposite
    }
}
