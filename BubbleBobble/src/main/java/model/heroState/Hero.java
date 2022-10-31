package model.heroState;

import controller.GameController;
import model.*;
import model.observer.*;

import javafx.scene.image.ImageView;
import model.projectile.BossProjectile;
import model.projectile.EnemyProjectile;
import model.projectile.HeroProjectile;
import utility.SoundEffect;

import java.util.ArrayList;

/**
 * A Hero is a DynamicObject that is controllable by the player.
 * Hero can shoot HeroProjectiles, shield from attacks, press w for a special attack and
 * collect Fruits for points, press for dash.
 */
public class Hero extends DynamicObject implements Subject {
    private static final int JUMP_SPEED = 22;
    private static final int TERMINAL_VELOCITY_X = 6;
    private static final int SIZE = 40;
    public static final int WALK = 5;
    static final int RUN = 10;
    static final double RUN_ACCEL = 5;
    static final int SHIELD_TIME = 120;
    private ImageView heroImageView;
    private GameController gameController;


    int shieldTimer;
    int stunTimer;
    public int shootDelay;
    private boolean readyToCharge;
    private boolean isOnAPlatform;
    private double jumpSpeed;

    private HeroState currentState;
    ArrayList<Observer> observers;

    /**
     * Hero constructor
     * @param gameController  The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     * @param heroImageView  The image view of the shoot enemy
     */
    public Hero(GameController gameController, int colNum, int rowNum, ImageView heroImageView) {
        //initializes hero
        super(gameController, colNum, rowNum, SIZE);
        this.gameController = gameController;
        isOnAPlatform = false;

        terminal_xVelocity = TERMINAL_VELOCITY_X;
        jumpSpeed = JUMP_SPEED;


        shieldTimer = SHIELD_TIME;

        stunTimer = 250;
        shootDelay = 0;
        readyToCharge = false;

        currentState = new NormalState(this);
        this.heroImageView = heroImageView;


        observers = new ArrayList<Observer>(); //create list for observers
        observers.add(new TotalScoreObserver(this,gameController));//add TotalScoreObserver in the list
        observers.add(new ScoreTextObserver(this,gameController));//add ScoreTextObserver in the list
    }

    /**
     * Hero shoot the projectile
     */
    public void shootProjectile() {
        //makes hero shoot projectile
        SoundEffect.SHOOT.play();
        ImageView heroProjectileImage = new ImageView("bubbleProjectile.png");//projectile image
        HeroProjectile heroProjectile = new HeroProjectile(gameController, x, y, direction, heroProjectileImage);
        gameController.addHeroProjectile(heroProjectile);
    }

    /**
     * Collide with mook
     */
    public void collideWithMook() {
        //handles colliding with a mook
        currentState.collideWithMook();

    }

    /**
     * Hero jump
     */
    void jump() {
        //handles jumping
        if (isOnAPlatform) {
            SoundEffect.JUMP.play();//play the sound if hero is on the platform
            y -= 1;
            yVelocity = -jumpSpeed;
            isOnAPlatform = false;
        }
    }

    /**
     * Hero collides with the wall
     */
    @Override
    public void collideWithWall() {
        // Nothing happens
    }

    /**
     * Handle when hero dies
     */
    public void die() {
        //handles death
        SoundEffect.DEATH.setToLoud();
        SoundEffect.DEATH.play();

        gameController.markToReset();//reset the game
        notifyObservers();


    }
    /**
     * Handle when colliding with boss projectile. Only boss has the ability.
     */
    public void collideWithBossProjectile(BossProjectile bossProjectile) {

        if (currentState instanceof ShieldState == false && currentState instanceof InvincibleState == false) {
            die(); //dies if not these two hero states
        }
        else if(currentState instanceof ShieldState == true){
            bossProjectile.markToRemove(); // after the collision between boss projectile and the shield
        }
    }
    /**
     * Handle when colliding with enemy projectile which is the ability for shoot enemy and boss
     */
    public void collideWithProjectile(EnemyProjectile enemyProjectile) {

        //if not shield state and invincible state hero dies
        if (currentState instanceof ShieldState == false && currentState instanceof InvincibleState == false) {
            die();//dies if not these two hero states
        }
        else if(currentState instanceof ShieldState == true){
            enemyProjectile.markToRemove();
        }
    }
    /**
     * Hero update
     */
    @Override
    public void update() {

        super.update();//updates position of hero
        currentState.update();//update according to different hero state

    }

    /**
     * Get the imageView
     * @return heroImageView
     */
    @Override
    public ImageView getImageView() {
        return heroImageView;
    }

    /**
     * Set the image view of the hero
     */
    @Override
    public void setImageView(ImageView imageView) {
        heroImageView = imageView;
    }

    /**
     * Handle when hero collides with floor
     */
    @Override
    public void collideWithFloor() {
        //handles collision with floor
        yVelocity = 0;
        if (!isOnAPlatform) {
            isOnAPlatform = true;
            SoundEffect.LAND.play();
        }
    }

    /**
     * Handle when the hero collide with ceiling
     */
    @Override
    public void collideWithCeiling() {

    }

    /**
     * get ready to charge
     * @return readyToCharge
     */
    public boolean getReadyToCharge(){
        return readyToCharge;
    }

    /**
     * set charge to ready
     */
    public void setChargeToReady() {
        //sets whether or not the hero is ready to charge the charge shot
        readyToCharge = true;
    }
    /**
     * set charge to false
     */
    public void setChargeToFalse() {
        //sets readyToCharge to false
        readyToCharge = false;
    }

    /**
     * For hero to switch state
     * @param heroState
     */
    public void setHeroState(HeroState heroState){
        this.currentState = heroState;
    }

    /**
     * get the state of the hero
     * @return currentState
     */
    public HeroState getHeroState(){
        return currentState;
    }

    /**
     * Add a observer to the observers arraylist
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Remove a observer from the observers arraylist
     * @param observer observer
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    /**
     * Notify all the observer in the arraylist observers.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update("-100"); // -100 score when hero dies

        }
    }
}
