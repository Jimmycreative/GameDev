package model.staticUnit;

import controller.GameController;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import model.DynamicObject;
import model.GameObject;


/**
 * The FloorUnit class creates floor units to be used for the world.<br>
 * A floor unit is a unit shaped like a square that is treated as a floor replace by imageview which has the same image as wall,<br>
 * with collision on the top, left, and right sides.<br>
 * The floor collides with any kind of dynamic object.<br>
 * When an enemy is bubbled, the enemy will still be stopped by a floor unit above it.
 */
public class FloorUnit extends StaticObject {
    private ImageView floorImageView;

    /**
     * FloorUnit constructor
     * @param gameController  The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     * @param floorImageView   The wall imageview
     */
    public FloorUnit(GameController gameController, int colNum, int rowNum, ImageView floorImageView) {
        super(gameController, colNum, rowNum);
        this.floorImageView = floorImageView;
    }
    /**
     * Handle when colliding with dynamic object. Check the obejct collides with the top of the floor or bottom of the floor
     * @param obj
     */
    public void collideWith(DynamicObject obj) {
        double top = obj.getY();
        double bottom = top + obj.getHeight();

        if (this.overlaps(obj) && obj.yVelocity > 0) {
            //System.out.println("collide floor");
            if (bottom <= y + height) {

                moveAboveUnit(obj);
                obj.collideWithFloor();
            }
            if (top > y){

                moveBelowUnit(obj);
                obj.collideWithCeiling();
            }
        }
    }

    @Override
    public ImageView getImageView() {
        return floorImageView;
    }

    @Override
    public void setImageView(ImageView imageView) {
        floorImageView = imageView;
    }
}
