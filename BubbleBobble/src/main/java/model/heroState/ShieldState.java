package model.heroState;

import javafx.scene.image.Image;

/**
 * Shield state cannot shoot won't die after collision with enemies and projectiles. In shield state hero cannot move.
 */
public class ShieldState implements HeroState{
    private Hero hero;
    /**
     * ShieldState constructor
     * @param hero
     */
    public ShieldState(Hero hero){
        this.hero = hero;
    }
    /**
     * Cannot move left
     */
    @Override
    public void keyLeft() {}

    /**
     * Cannot move right
     */
    @Override
    public void keyRight() {

    }

    /**
     * Cannot jump
     */
    @Override
    public void keyUp() {

    }

    /**
     * Cannot move shoot
     */
    @Override
    public void keyE() {

    }

    /**
     * Back to normal state and will check the direction to replace back the image view according to direction.
     */
    @Override
    public void releaseQ() {
        if (hero.direction<0){
            hero.getImageView().setImage(new Image("HeroLeft.png"));
        }
        else if (hero.direction>0){
            hero.getImageView().setImage(new Image("HeroRight.png"));
        }
        hero.setHeroState(new NormalState(hero));//set back to normal state
    }

    /**
     * Cannot press again Q when you still press q, so do nothing.
     */
    @Override
    public void keyQ() {

    }

    /**
     * Check if the shield timer is smaller than zero hero will change to stun state.
     */
    @Override
    public void update() {
        hero.shieldTimer -= 1;
        if (hero.shieldTimer <= 0) {
            hero.shieldTimer = 0;
            hero.getImageView().setImage(new Image("stun.png")); //stunned hero image
            hero.setHeroState(new StunState(hero));
        }
    }

    /**
     * Won't die since shield state
     */
    @Override
    public void collideWithMook() {

    }
    /**
     * Won't dash since shield state cannot move.
     */
    @Override
    public void keySpace() {

    }
}
