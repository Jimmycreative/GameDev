package model.projectile;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import launcher.App;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import static org.junit.jupiter.api.Assertions.assertTrue;


class EnemyProjectileTest extends ApplicationTest {

    /**
     * Check whether the enemy projectile will be removed or not if the timer smaller -100. Timer starts from 60
     */
    @Test
    void updateTest() {
        EnemyProjectile enemyProjectile = new EnemyProjectile(null,10,20,0,null);

        for (int i=0;i<162;i++){
            enemyProjectile.update();
        }
        assertTrue(enemyProjectile.canRemove);
    }


    /**
     * Check the return imageview
     */
   @Test
    void getImageViewTest() {
       ImageView enemyImageView = new ImageView("MonsterLeft.png");
       EnemyProjectile enemyProjectile = new EnemyProjectile(null,10,20,0,enemyImageView);
       Assertions.assertEquals(enemyProjectile.getImageView(),enemyProjectile.getImageView());
    }

    /**
     * Check whether the Image view is set correctly.
     */
    @Test
    void setImageView() {
        ImageView enemyImageView = new ImageView("MonsterLeft.png");
        EnemyProjectile enemyProjectile = new EnemyProjectile(null,10,20,0,enemyImageView);
        enemyProjectile.setImageView(enemyImageView);
        Assertions.assertEquals(enemyImageView,enemyProjectile.getImageView());
    }


}