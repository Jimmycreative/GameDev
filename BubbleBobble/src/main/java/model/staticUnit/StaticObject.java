package model.staticUnit;

import controller.GameController;
import javafx.geometry.Point2D;
import launcher.App;
import model.DynamicObject;
import model.GameObject;

/**
 * Static object is wall unit ceiling unit and floor unit which all of them cannot move.
 */
public abstract class StaticObject extends GameObject {

    /**
     * StaticObject constructor
     * @param gameController  The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     */
    public StaticObject(GameController gameController, int colNum, int rowNum) {
        super(gameController, colNum, rowNum, App.UNIT_SIZE, App.UNIT_SIZE);
    }
    /**
     * Handle when colliding with dynamic object
     * @param obj
     */
    public abstract void collideWith(DynamicObject obj);
    /**
     * Move the object above
     * @param obj
     */
    public void moveAboveUnit(GameObject obj) {
        obj.moveTo(new Point2D(obj.getX(), y - obj.getHeight()));
    }
    /**
     * Move the object below
     * @param obj
     */
    public void moveBelowUnit(GameObject obj) {
        obj.moveTo(new Point2D(obj.getX(), y + height));
    }
    /**
     * Move the object left
     * @param obj
     */
    public void moveLeftOfUnit(GameObject obj) {
        obj.moveTo(new Point2D(x - obj.getWidth(), obj.getY()));
    }
    /**
     * Move the object right
     * @param obj
     */
    public void moveRightOfUnit(GameObject obj) {
        obj.moveTo(new Point2D(x + width, obj.getY()));
    }
}
