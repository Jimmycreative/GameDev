package model.heroState;

import javafx.scene.image.Image;
import utility.SoundEffect;

/**
 * Normal state can shoot can shield and will die after collision with enemies and projectiles.
 */
public class NormalState implements HeroState{
    private Hero hero;
    /**
     * NormalState constructor
     * @param hero
     */
    public NormalState(Hero hero){
        this.hero = hero;
    }
    /**
     * Handle when press left. Direction becomes -1 and imageview become left.
     */
    @Override
    public void keyLeft() {
        hero.xAccel = -hero.RUN_ACCEL;
        hero.direction = -1;
        hero.getImageView().setImage(new Image("HeroLeft.png"));
    }
    /**
     * Handle when press right. Direction becomes 1 and imageview become right.
     */
    @Override
    public void keyRight() {
        hero.xAccel = hero.RUN_ACCEL;
        hero.direction = 1;
        hero.getImageView().setImage(new Image("HeroRight.png"));
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
     * Handle when press q. Shield! Change the hero state to shield.
     */
    @Override
    public void keyQ() {
        hero.xVelocity = 0;
        hero.xAccel = 0;
        hero.getImageView().setImage(new Image("heroInShield.png"));
        hero.setHeroState(new ShieldState(hero));
    }

    /**
     * Add the shield timer when smaller than the shield time in normal state
     */
    @Override
    public void update() {
        if (hero.shieldTimer < hero.SHIELD_TIME) {
            hero.shieldTimer += 1;
        }
    }
    /**
     * Handle collision with mook. Hero will die.
     */
    @Override
    public void collideWithMook() {
        hero.die();
    }

    /**
     * Handle when press space. Hero dash.
     */
    @Override
    public void keySpace() {
        hero.terminal_xVelocity = hero.RUN;
    }
}
