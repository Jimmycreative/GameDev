package model.projectile;


import controller.GameController;
import javafx.scene.image.ImageView;
import model.enemy.Enemy;
import model.heroState.Hero;
import model.projectile.Projectile;

/**
 * The EnemyProjectile class handles the specificities with the projectile being shot from a shootEnemy or the boss.<br>
 * It also can only hurt a hero.
 */
public class EnemyProjectile extends Projectile {
    private static final int SIZE = 35;
    private static final int SPEED = 15;
    private static final int TERMINAL_VELOCITY_Y = 5;

    private boolean isActive;
    private int activeFrames;
    private int timer;
    private ImageView enemyProjectileImageView;
    /**
     * HeroProjectile constructor
     * @param gameController The main controller of the game
     * @param x              The x coordinate of the enemy projectile
     * @param y              The y coordinate of the enemy projectile
     * @param direction      The direction of the enemy projectile
     * @param enemyProjectileImageView The image view of the enemy projectile
     */
    public EnemyProjectile(GameController gameController, int x, int y, int direction, ImageView enemyProjectileImageView) {
        super(x, y, SIZE, gameController);
        this.direction = direction;

        xVelocity = SPEED;
        yAccel = 0;

        isActive = true;
        activeFrames = 60;
        timer = activeFrames;
        this.enemyProjectileImageView = enemyProjectileImageView;
    }


    /**
     * Update the velocity of the enemy projectile and turn it into inactive in a time
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

        if (timer < -100) {
            markToRemove();
        }
        timer -= 1;
    }

    /**
     * Get the enemy projectile imageview
     * @return enemyProjectileImageView
     */
    @Override
    public ImageView getImageView() {
        return enemyProjectileImageView;
    }

    /**
     * Set the image view of the enemy projectile
     * @param imageView
     */
    @Override
    public void setImageView(ImageView imageView) {
        enemyProjectileImageView = imageView;
    }

    /**
     * Update the velocity of the enemy projectile
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
    @Override  //override from projectile
    public void collideWith(Hero hero) {
        if(this.overlaps(hero) && isActive) {
            hero.collideWithProjectile(this);
        }
    }


    @Override
    public void collideWith(Enemy enemy) {
        //Nothing happens
    }

    /**
     * Get the isActive boolean
     * @return isActive
     */
    public boolean getIsActive(){return isActive;}

}

