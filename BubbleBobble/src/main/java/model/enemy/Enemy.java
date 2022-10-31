package model.enemy;
import model.DynamicObject;
import model.staticUnit.StaticObject;
import model.heroState.Hero;
import model.observer.*;
import controller.GameController;

import javafx.scene.image.ImageView;

import java.util.ArrayList;


/**
 * An Enemy is a non-controllable DynamicObject that kills the Hero whenever it or its projectile comes in contact.<br>
 * Enemies are able to be bubbled and free themselves from these bubbles after 5 seconds.<br>
 * Enemies change direction at random intervals, when hitting a wall, and when hitting Hero's shield.<br>
 * Enemies jump at random intervals as well.<br>
 * 3 types of enemies. One cannot shoot One can shoot One is the boss.<br>
 * It's a dynamic object. It is also the subject in observer pattern.
 */
public abstract class Enemy extends DynamicObject implements Subject {

    protected static final int JUMP_SPEED = 18;
    protected static final int TERMINAL_VELOCITY_X = 4;
    protected static final int BUBBLED_FRAMES = 300;
    protected static final double CHANGE_MOVEMENT_CHANCE = 0.01;

    protected boolean turningAwayFromShield;
    protected int turningAwayCount;
    private boolean isOnAPlatform;
    private double jumpSpeed;
    private ImageView enemyImageView;

    ArrayList<Observer> observers;
    public boolean isBubbled;

    /**
     * Enemy connstructor
     * @param gameController  The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     * @param enemyImageView  The image view of the shoot enemy
     */
    public Enemy(GameController gameController, int colNum, int rowNum, ImageView enemyImageView, int size) {
        //initializes enemy
        super(gameController, colNum, rowNum, size);

        isOnAPlatform = false;
        jumpSpeed = JUMP_SPEED;
        terminal_xVelocity = TERMINAL_VELOCITY_X;

        xAccel = 1.5;
        direction = 1;
        if (Math.random() < 0.5) { //reverse direction when initializing
            reverseDirection();
        }

        turningAwayFromShield = false;
        turningAwayCount = 10;
        this.enemyImageView = enemyImageView;

        observers = new ArrayList<Observer>(); //create list for observers
        observers.add(new TotalScoreObserver(this,gameController));//add TotalScoreObserver in the list
        observers.add(new ScoreTextObserver(this,gameController));//add ScoreTextObserver in the list

    }

    /**
     * Handle when enemy collides with floor
     */
    @Override
    public void collideWithFloor() {
        //handles floor collision values
        yVelocity = 0;
        if (!isOnAPlatform) {
            isOnAPlatform = true;
        }
    }
    /**
     * Handle when enemy collides with wall
     */
    @Override
    public void collideWithWall() {
        //handles what to do on collision with a wall
        reverseDirection();
    }

    /**
     * Handle when enemy collides with ceiling
     */
    @Override
    public void collideWithCeiling() {
        //handles ceiling collision values
        yVelocity = 0;
    }

    /**
     *Update enemy checks in the bubble or not and change the movement.
     */
    public abstract void updateEnemy();

    /**
     * Enemy update
     */
    @Override
    public void update() {
        super.update();//update the position in the parent class. DynamicObject
        updateEnemy();//update in the child class
    }

    /**
     * Enemy jump check also if it is on platform
     */
    void jump() {
        //handles jumping
        if (isOnAPlatform) {
            y -= 1;
            yVelocity = -jumpSpeed;
            isOnAPlatform = false;
        }
    }

    /**
     * Different types of enemies override based on different behavior
     */
    public abstract void shootProjectile();

    /**
     * Handle when enemy dies
     */
    public void die() {
        //handles what to do on death
        markToRemove();
        notifyObservers();
    }

    /**
     * Get the imageView
     * @return enemyImageView
     */
    @Override
    public ImageView getImageView(){
        return enemyImageView;
    }

    /**
     * Set the image view of the enemy
     */
    @Override
    public void setImageView(ImageView imageView) {
        enemyImageView = imageView;
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
            observer.update("+50"); //50 is the score when kill the enemy
        }
    }
    /**
     * Handle collision with wall ceiling floor.
     * @param staticObject
     */
    public abstract void collideWith(StaticObject staticObject);

    /**
     * Handle collision with hero
     * @param hero
     */
    public abstract void collideWith(Hero hero);

    /**
     * Handle the collision with projectile
     */
    public abstract void collideWithProjectile();

    
}


