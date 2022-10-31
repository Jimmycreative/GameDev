package model.heroState;

import javafx.scene.image.Image;

/**
 * Stun state cannot move. Will die when colliding with enemies and projectiles
 */
public class StunState implements HeroState {
    private Hero hero;
    /**
     * StunState constructor
     * @param hero
     */
    public StunState(Hero hero){
        this.hero = hero;
    }
    /**
     * Cannot move left
     */
    @Override
    public void keyLeft() {

    }

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
     * Cannot shoot
     */
    @Override
    public void keyE() {

    }
    /**
     * Cannot turn away from shield since not in shield state
     */
    @Override
    public void releaseQ() {

    }

    /**
     * Cannot shield
     */
    @Override
    public void keyQ() {

    }

    /**
     * CHeck the stun timer for coming back to normal state.
     */
    @Override
    public void update() {
        hero.stunTimer -= 1;
        if (hero.stunTimer <= 0) {

            hero.stunTimer = 250;
            hero.shieldTimer = hero.SHIELD_TIME;
            hero.getImageView().setImage(new Image("HeroLeft.png"));//turn back to the original hero image
            hero.setHeroState(new NormalState(hero));
        }
    }

    /**
     * Hero will dies
     */
    @Override
    public void collideWithMook() {
        hero.die();
    }

    /**
     * Cannot dash
     */
    @Override
    public void keySpace() {

    }
}
