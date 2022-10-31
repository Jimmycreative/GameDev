package model.projectile;

import controller.GameController;
import javafx.scene.image.ImageView;
import model.enemy.Enemy;
import model.heroState.Hero;
import model.projectile.Projectile;

/**
 * The HeroProjectile class handles the specificities with the projectile being shot from a hero.<br>
 * After the collision with enemy, enemy will become bubble.
 */
public class HeroProjectile extends Projectile {
    private static final int SIZE = 25;
    private static final int SPEED = 15;
    private static final int TERMINAL_VELOCITY_Y = 5;

    private boolean isActive;
    private int activeFrames;
    public int timer;
    private ImageView heroProjectileImageView;

    /**
     * HeroProjectile constructor
     * @param gameController The main controller of the game
     * @param x              The x coordinate of the hero projectile
     * @param y              The y coordinate of the hero projectile
     * @param direction      The direction of the hero projectile
     * @param heroProjectileImageView The image view of the hero projectile
     */
    public HeroProjectile(GameController gameController, int x, int y, int direction, ImageView heroProjectileImageView) {
        super(x, y, SIZE, gameController); //all the projectile width and height are the same, so only pass a size.
        this.direction = direction;

        xVelocity = SPEED;
        yAccel = 0;

        isActive = true;
        activeFrames = 35;
        timer = activeFrames;
        this.heroProjectileImageView = heroProjectileImageView;
    }

    /**
     * Update the velocity of the hero projectile and turn it into inactive in a time
     */
    @Override
    public void update() {
        y += yVelocity;
        x += xVelocity * direction;
        updateVelocity();

        if(y < 25) {
            y = 25;
        }

        if (timer < 0) {
            isActive = false;
        }

        if (timer < -200) {
            markToRemove();
        }
        timer -= 1;
    }

    /**
     * Get the hero projectile imageview
     * @return heroProjectileImageView
     */
    @Override
    public ImageView getImageView() {
        return heroProjectileImageView;
    }

    /**
     * Set the image view of the hero projectile
     */
    @Override
    public void setImageView(ImageView imageView) {
        heroProjectileImageView = imageView;
    }

    /**
     * Update the velocity of the hero projectile
     */
    private void updateVelocity() {
        if (xVelocity > 0) {
            xVelocity -= 0.25;
        } else {
            xVelocity = 0;
        }

        if (Math.abs(yVelocity) < TERMINAL_VELOCITY_Y && !isActive) {

            yVelocity -= 0.1;
        }
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
    @Override
    public void collideWith(Hero hero) {
        // Nothing happens
    }
    /**
     * Handle when colliding with enemy. Enemy will become bubble except the boss.
     * @param enemy
     */
    @Override
    public void collideWith(Enemy enemy) {
        if (this.overlaps(enemy) && isActive) {

            enemy.collideWithProjectile();
            isActive = false;
        }
    }
    /**
     * Get the isActive boolean
     * @return isActive
     */
    public boolean getIsActive(){return isActive;}
}
