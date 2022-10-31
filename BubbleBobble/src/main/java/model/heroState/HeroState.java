package model.heroState;

/**
 * The state of the hero. Four states: Normal, Invincible, Shield, and Stun
 */
public interface HeroState {
    /**
     * Handle when press left
     */
    void keyLeft();
    /**
     * Handle when press right
     */
    void keyRight();
    /**
     * Handle when press up
     */
    void keyUp();
    /**
     * Handle when press E
     */
    void keyE();
    /**
     * Handle when release Q
     */
    void releaseQ();
    /**
     * Handle when press Q
     */
    void keyQ();
    /**
     * Handle hero update
     */
    void update();
    /**
     * Handle when  hero collides with mook
     */
    void collideWithMook();
    /**
     * Handle when press space
     */
    void keySpace();
}
