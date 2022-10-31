package model.staticUnit;

import controller.GameController;
import javafx.scene.image.ImageView;

import javafx.geometry.Point2D;
import model.DynamicObject;
import model.GameObject;

/**
 * The CeilingUnit class creates ceiling units to be used for the world.<br>
 * It's a static object which has the same image as wall and floor.<br>
 * A ceiling unit is a unit shaped like a square that is treated as a ceiling, with collision on all four sides.<br>
 * The ceiling collides with any kind of game object.<br>
 * Even if a game object is on top of a ceiling, the game object will be pushed down.
 */
public class CeilingUnit extends StaticObject {

    private ImageView ceilingImageView;
    /**
     * CeilingUnit constructor
     * @param gameController  The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     * @param ceilingImageView   The wall imageview
     */
    public CeilingUnit(GameController gameController, int colNum, int rowNum, ImageView ceilingImageView) {
        super(gameController, colNum, rowNum);
        this.ceilingImageView = ceilingImageView;
    }
    /**
     * Handle when colliding with dynamic object. Move the game object below the ceiling
     * @param obj
     */
    public void collideWith(DynamicObject obj) {
        if (this.overlaps(obj)) {
            moveBelowUnit(obj);
            obj.collideWithCeiling();
        }
    }

    @Override
    public ImageView getImageView() {
        return ceilingImageView;
    }

    @Override
    public void setImageView(ImageView imageView) {
        ceilingImageView = imageView;
    }
}

