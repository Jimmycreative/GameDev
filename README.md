##BubbleBobble

## Refactoring
### Maintenance
- Convert all the code from Swing to Javafx.
- Encapsulation: 
```
//getter and setter methods.
public abstract ImageView getImageView();
public abstract void setImageView(ImageView imageView);
```
- Meaningful variable, method, or class name.
- Divided GameObject class into DynamicObject and StaticObject. This removes a lot of duplicate code usage.
```
//move from GameObject to class DynamicObject
protected void update();
public void reverseDirection()
public abstract void collideWithFloor();
public abstract void collideWithCeiling();
public abstract void collideWithWall();
```
- Single Responsibility with Extract methods. The updatePosition method in the InteractableWorld(GameController) is too large.  Replace three arrayList wallUnits, floorUnits, and ceilingUnits by
staticObjects arrayList and create staticObjectCollision method to replace the duplicate code from line 120~173 in the sample code.

```
//Since in my program I have three types of projectiles,
//I use one arrayList projectiles to store all of them. 
//This remove the duplicate code:

for (EnemyProjectile enemyProjectile : enemyProjectiles) {
	enemyProjectile.update();
	if (enemyProjectile.canRemove) {
		toBeRemoved.add(enemyProjectile);
	}
}
for (HeroProjectile heroProjectile : heroProjectiles) {
	heroProjectile.update();
	if (heroProjectile.canRemove) {
		toBeRemoved.add(heroProjectile);
	}
}
for (HeroProjectile heroProjectile : heroProjectiles) {
	for (Hero hero : heroes) {
		heroProjectile.collideWith(hero);
	}
	for (Enemy enemy : enemies) {
		heroProjectile.collideWith(enemy);
	}
}
for (EnemyProjectile enemyProjectile  : enemyProjectiles) {
	for (Hero hero : heroes) {
		enemyProjectile.collideWith(hero);
	}
	for (Enemy enemy : enemies) {
		enemyProjectile.collideWith(enemy);
	}
}
```
- Remove paintComponent method in InteractableWorld, it can be simplified by using setLayOutX and setLayoutY method [setImageViewPosition](BubbleBobble/src/main/java/controller/GameController.java) for update.
No need to repaint each time in the GamePanel repaintTimer.
### Design pattern
-[State pattern](BubbleBobble/src/main/java/model/heroState): The reason for doing this is there are a lot of if else statement to determine whether hero is stun or shield. State can be changed using setHeroState in the Hero class.

```
//State can be changed using setHeroState in the Hero class.
//Example below shows the difference.
Sample code:
public void collideWithMook() {
    //handles colliding with a mook
	if (!isShielding) {
		die();
	}
}
My code:
public void collideWithMook() {
   //handles colliding with a mook
   currentState.collideWithMook();

}
```
-[Observer pattern](BubbleBobble/src/main/java/model/observer): When an enemy dies or hero dies, or hero collect a fruit these three situations will all cause the temporary display text like (+70,-100) show on the screen and the total score on the right top corner to update at the same time.
Subjects like enemy, fruit, and hero will notify all the observers like ScoreTextObserver and TotalScoreObserver.
\
-[Strategy pattern](BubbleBobble/src/main/java/model/fruitStrategy): I created another [3 fruits](BubbleBobble/src/main/java/model/fruit) which are BubbleFruit for hero to press the keycode w, InvincibleFruit for hero to be invincible in 4 seconds, and one fruit for adding another life. 
\
-[Factory pattern](src/main/java/model/GameObjectFactory.java): Create the gameObject according to different maps.([GameObjectFactory](BubbleBobble/src/main/java/model/GameObjectFactory.java)).
\
-[Singleton pattern](BubbleBobble/src/main/java/view/GameView.java): GameView class.
\
-[MVC pattern](BubbleBobble/src/main/java): Structure of the program: model, view, controller. I remove the drawOn method in the models is becuase it's obviously the controller job. I also move the keylistener for hero's operation into the [GameController](BubbleBobble/src/main/java/controller/GameController.java).
GameController is the main core of this program. Originally, the gameTimer is put in the GamePanel in the sample code. I also move to the GameController. For the naming convention InteractableWorld becomes GameController GamePanel becomes GameView. 

## Game features added
### Simple extension
- Rectangles in the game has been replaced by ImageView.
- A start screen with different buttons to redirect to different page.
- Setting page for player to change the **game difficulty**, **wall color**, and the **background**.
- Info page: game operations.
- Player name input page get the player name.
- High score board when game over.
- Player win page.
### Three best extension of the game
- Different game difficulty has different world. For easy, there are three levels, but all the enemies in three level cannot shoot.
**For medium one, the first level enemies cannot shoot, the second level one enemy can shoot**, and the last level two enemies can shoot.
For the hard level, there is only one level and there is a boss. **Move to the next level when enemies all dies and fruit all collected**.
- Boss in the game each time player shoot a bubble to it, it will decrease 0.1 of life.
- Power up fruit. Every level of the game there is going to have a power up fruit for hero to get super power.
