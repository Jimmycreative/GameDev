package model.heroState;

import javafx.scene.image.Image;

/**
 * A state of the hero which won't die when colliding with enemy or enemy projectile.
 */
public class InvincibleState implements HeroState{
    private Hero hero;
    private int invincibleTimer = 240; //4 seconds

    /**
     * InvincibleState constructor
     * @param hero
     */
    public InvincibleState(Hero hero){
        this.hero = hero;
    }

    /**
     * Handle when press left. Direction becomes -1 and imageview become left.
     */
    @Override
    public void keyLeft() {
        hero.xAccel = -hero.RUN_ACCEL;
        hero.direction = -1;
        hero.getImageView().setImage(new Image("InvincibleLeft.png"));
    }
    /**
     * Handle when press right. Direction becomes 1 and imageview become right.
     */
    @Override
    public void keyRight() {
        hero.xAccel = hero.RUN_ACCEL;
        hero.direction = 1;
        hero.getImageView().setImage(new Image("InvincibleRight.png"));
    }

    /**
     * Handle when press up. Jump.
     */
    @Override
    public void keyUp() {
        hero.jump();

    }

    /**
     * Handle when press E. Shoot the projectile.
     */
    @Override
    public void keyE() {
        hero.shootDelay -= 1;
        if (hero.shootDelay <= 0) {

            hero.shootProjectile();
            hero.shootDelay = 10;
        }
    }

    /**
     * Handle when release q. Do nothing
     */
    @Override
    public void releaseQ() {

    }

    /**
     * Handle when press q. Do nothing
     */
    @Override
    public void keyQ() {

    }

    /**
     * Handle update to check the invincible timer.
     */
    @Override
    public void update() {
        invincibleTimer-=1;
        if (invincibleTimer==0){
            hero.setHeroState(new NormalState(hero));
        }
    }

    /**
     * Handle collide with mook. Nothing happens
     */
    @Override
    public void collideWithMook() {

    }

    /**
     * Handle when press space. Do nothing
     */
    @Override
    public void keySpace() {

    }
}
