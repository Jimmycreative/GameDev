package model.staticUnit;

import controller.GameController;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import model.DynamicObject;
import model.GameObject;


/**
 * The WallUnit class creates wall units to be used for the world.<br>
 * A wall unit is an unit shaped like a square which replace by the image view wall with different colors,<br>
 * with collision on all four sides.<br>
 * The wall collides with any kind of dynamic object.<br>
 */
public class WallUnit extends StaticObject {

    private ImageView wallImageView;
    /**
     * WallUnit constructor
     * @param gameController  The main controller of the game
     * @param colNum          The column number on the map
     * @param rowNum          The row number on the map
     * @param wallImageView   The wall imageview
     */
    public WallUnit(GameController gameController, int colNum, int rowNum, ImageView wallImageView) {
        super(gameController, colNum, rowNum);
        this.wallImageView = wallImageView;
    }

    /**
     * Handle when colliding with dynamic object.Check the object is left from the wall or right from the wall.
     * @param obj
     */
    public void collideWith(DynamicObject obj) {
        double center = (obj.getHitbox().getMinX()+obj.getHitbox().getMaxX())/2;
        if (this.overlaps(obj)) {
            if (center > (this.getHitbox().getMinX()+this.getHitbox().getMaxX())/2) {
                moveRightOfUnit(obj);
                obj.collideWithWall();
            } else if (center < (this.getHitbox().getMinX()+this.getHitbox().getMaxX())/2){
                moveLeftOfUnit(obj);
                obj.collideWithWall();
            } else {
                moveBelowUnit(obj);
                obj.collideWithCeiling();
            }
        }
    }


    @Override
    public ImageView getImageView() {
        return wallImageView;
    }

    @Override
    public void setImageView(ImageView imageView) {
        wallImageView = imageView;
    }
}
