package model.projectile;

import controller.GameController;
import model.DynamicObject;
import model.enemy.Enemy;
import model.heroState.Hero;

/**
 * Projectile shoot from shootEnemy or boss enemy or hero.<br>
 * Also a dynamic object. 3 types heroProjectile, BossProjectile, and enemyProjectile.
 */
public abstract class Projectile extends DynamicObject {

    /**
     * Projectile constructor
     * @param x              The x coordinate of the projectile
     * @param y              The y coordinate of the projectile
     * @param size           The size of the projectile
     * @param gameController The main controller of the game
     */
    public Projectile(int x, int y, int size, GameController gameController) {
        super(x, y, size, gameController);
    }
    /**
     * Handle when collide with enemies
     * @param enemy
     */
    public abstract void collideWith(Enemy enemy);

    /**
     * Handle when collide with hero
     * @param
     */
    public abstract void collideWith(Hero hero);
}
