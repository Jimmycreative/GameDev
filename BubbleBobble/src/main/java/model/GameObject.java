package model;

import controller.GameController;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import launcher.App;
import javafx.geometry.Rectangle2D;


/**
 * GameObjects are the objects on the controller.Game controller screen.<br>
 * Every GameObject has a velocity, acceleration, position, direction.<br>
 * It can be divided into two subclasses which are staticObject and DynamicObject since<br>
 * some of the object we don't need to handle the velocity and the collision.
 */
public abstract class GameObject {

    protected static final int GRAVITY = 1;
    private static final int TERMINAL_FALL_SPEED = 20;

    public GameController gameController;
    public int x, y;
    public int width, height;

    public double xVelocity, yVelocity;
    public double xAccel, yAccel;
    public int terminal_xVelocity, terminal_yVelocity;

    public boolean canRemove;
    public int direction;

    /**
     * GameObject Constructor
     * @param gameController The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     * @param width            The width of the game object
     * @param height            The height of the game object
     */
    public GameObject(GameController gameController, int colNum, int rowNum, int width, int height) {
        this.gameController = gameController;
        x = colNum * App.UNIT_SIZE;
        y = rowNum * App.UNIT_SIZE;
        this.width = width;
        this.height = height;

        xVelocity = 0;
        yVelocity = 0;
        xAccel = 0;
        yAccel = GRAVITY;
        terminal_xVelocity = 0;
        terminal_yVelocity = TERMINAL_FALL_SPEED;
        canRemove = false;
        direction = -1;
    }
    /**
     * GameObject Constructor
     * @param x                The x coordinate of the game object
     * @param y                The y coordinate of the game object
     * @param width            The width of the game object
     * @param height           The height of the game object
     * @param gameController   The main controller of the game
     */
    public GameObject(int x, int y, int width, int height, GameController gameController) {
        //initializes the game object
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gameController = gameController;

        xVelocity = 0;
        yVelocity = 0;
        xAccel = 0;
        yAccel = GRAVITY;
        terminal_xVelocity = 0;
        terminal_yVelocity = TERMINAL_FALL_SPEED;
        canRemove = false;
        direction = -1;
    }

    /**
     * get the x coordinate
     */
    public double getX() {
        //returns x coordinate of upper left corner
        return x;
    }
    /**
     * get the y coordinate
     */
    public double getY() {
        //returns y coordinate of upper left corner
        return y;
    }
    /**
     * get the width of the game object
     */
    public double getWidth() {
        //returns width
        return width;
    }
    /**
     * get the height of the game object
     */
    public double getHeight() {
        //returns height
        return height;
    }
    /**
     * mark the game object to be removed
     */
    public void markToRemove() {
        //sets whether or not something can be removed
        canRemove = true;
    }
    /**
     * Get the hit box to handle the collision
     */
    public Rectangle2D getHitbox(){
        //sets hitbox for each game object
        return new Rectangle2D(x, y, width, height);
    }

    /**
     * check overlap
     * @param obj
     */
    protected boolean overlaps(GameObject obj) {
        //checks if two objects overlap or collide
        return getHitbox().intersects(obj.getHitbox());
    }
    /**
     * move to another new x,y coordinate
     * @param point
     */
    public void moveTo(Point2D point) {
        //moves object to a point
        x = (int) point.getX();
        y = (int) point.getY();
    }
    /**
     * get image view
     */
    public abstract ImageView getImageView();
    /**
     * set image view
     * @param imageView
     */
    public abstract void setImageView(ImageView imageView);
}
