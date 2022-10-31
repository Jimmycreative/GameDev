package model.fruit;

import controller.GameController;
import model.DynamicObject;
import model.heroState.Hero;
import model.observer.*;
import javafx.scene.image.ImageView;
import utility.SoundEffect;

import java.util.ArrayList;

/**
 * The Fruit class handles how the fruit is created and interacts with the hero.<br>
 * The normal fruit is created after a bubble containing an enemy is popped.<br>
 * The other three are the power up fruit which will show only one of the three in each level.<br>
 * Four types of fruits one normal fruit, one bubble fruit, one invincible fruit,and one life fruit.<br>
 * It's a dynamic object. It is also the subject in observer pattern.
 */
public abstract class Fruit extends DynamicObject implements Subject {
    private static final int SIZE = 40;
    private static final int TERMINAL_VELOCITY_Y = 10;
    private ImageView fruitImageView;

    private boolean readyToCollect;
    private int pointValue;
    ArrayList<Observer> observers;

    /**
     * Fruit constructor
     * @param x              The x coordinate of the fruit
     * @param y              The y coordinate of the fruit
     * @param gameController The main controller of the game
     * @param fruitImageView The imageView of the fruit
     * @param pointValue     The point of the fruit. Different types different points.
     */
    public Fruit(int x, int y, GameController gameController, ImageView fruitImageView, int pointValue) {
        //initializes fruit
        super(x, y, SIZE, gameController);
        terminal_yVelocity = TERMINAL_VELOCITY_Y;
        readyToCollect = false;

        this.fruitImageView = fruitImageView;

        observers = new ArrayList<Observer>(); //create list for observers
        observers.add(new TotalScoreObserver(this,gameController));
        observers.add(new ScoreTextObserver(this,gameController));
        this.pointValue = pointValue;
    }
    /**
     * Handle when fruit collides with hero. If collides notify to all the observers and remove the fruit on the screen.
     */
    public void collideWith(Hero hero) {
        //checks for collision with hero and tells it what to do if it is colliding

        if (this.overlaps(hero) && readyToCollect) {
            SoundEffect.FRUIT.setToLoud();
            SoundEffect.FRUIT.play();

            readyToCollect = false;
            powerUp();

            markToRemove();
            notifyObservers();
        }
    }
    /**
     * Handle some of the fruit which has super power. 3 types of fruit has super power.
     */
    public abstract void powerUp();

    /**
     * Handle when fruit collides with floor. Turn to readyToCollect when canRemove is false
     */
    @Override
    public void collideWithFloor() {
        yVelocity = 0;
        if (!canRemove) {

            readyToCollect = true;
        }
    }
    /**
     * Handle when fruit collides with ceiling
     */
    @Override
    public void collideWithCeiling() {
        // Nothing happens
    }
    /**
     * Handle when fruit collides with wall
     */
    @Override
    public void collideWithWall() {
        // Nothing happens
    }
    /**
     * Get the imageView
     * @return enemyImageView
     */
    @Override
    public ImageView getImageView() {
        return fruitImageView;
    }
    /**
     * Set the image view of the enemy
     */
    @Override
    public void setImageView(ImageView imageView) {

    }
    /**
     * Add an observer from the observers arraylist
     * @param observer observer
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    /**
     * Remove an observer from the observers arraylist
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
            observer.update("+"+pointValue); //50 is the score when kill the enemy + for increasing
        }
    }
}
