package model;

import controller.GameController;

/**
 * DynamicObject is the game object like projectiles, hero, enemies, bubble and fruit.
 */
public abstract class DynamicObject extends GameObject{
    private static final double STATIC_FRICTION = 0.1;

    /**
     * DynamicObject Constructor
     * @param gameController The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     * @param size            The size of the dynamic object
     */
    public DynamicObject(GameController gameController, int colNum, int rowNum, int size) {
        super(gameController, colNum, rowNum, size, size);
    }
    /**
     * DynamicObject Constructor
     * @param x                 The coordinate x of the object
     * @param y                 The coordinate y of the object
     * @param size              The size of the dynamic object
     * @param gameController    The main controller of the game
     */
    public DynamicObject(int x, int y, int size, GameController gameController) {
        super(x, y, size, size, gameController);
    }

    /**
     * Update the x y velocity of the dynamic object.
     */
    public void update() {
        //general update method of every dynamic object
        if (Math.abs(xVelocity) < terminal_xVelocity) {
            xVelocity += xAccel;
        }
        if (Math.abs(xVelocity) > STATIC_FRICTION) {
            if (xVelocity < 0) {
                xVelocity += 1;
            } else {
                xVelocity -= 1;
            }
            x += xVelocity;
        }

        if (yVelocity < terminal_yVelocity) {
            yVelocity += yAccel;
        }
        y += yVelocity;
    }
    /**
     * Reverse the dynamic object direction
     */
    public void reverseDirection() {
        //reverses game object's direction
        xAccel *= -1;
        direction *= -1;
    }
    /**
     * Handle when colliding with floor
     */
    public abstract void collideWithFloor();
    /**
     * Handle when colliding with ceiling
     */
    public abstract void collideWithCeiling();
    /**
     * Handle when colliding with wall
     */
    public abstract void collideWithWall();
}
