package controller;

import controller.state.DifficultyState;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import launcher.App;
import model.*;
import model.enemy.BossEnemy;
import model.enemy.Enemy;
import model.enemy.NotShootEnemy;
import model.enemy.ShootEnemy;
import model.fruit.Fruit;
import model.heroState.Hero;
import model.projectile.BossProjectile;
import model.projectile.EnemyProjectile;
import model.projectile.HeroProjectile;
import model.projectile.Projectile;
import model.staticUnit.CeilingUnit;
import model.staticUnit.FloorUnit;
import model.staticUnit.StaticObject;
import model.staticUnit.WallUnit;
import utility.SoundEffect;
import view.GameView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Game controller handles all game's operations: <br>
 * updating positions, checking for collisions, handling switching levels, <br>
 * draw the map according to different difficulty of the game, the text shown on the <br>
 * screen and removing objects.
 */
public class GameController {

    private final ArrayList<StaticObject> staticObjects;//include wallUnits, floorUnits, and ceilingUnits
    private final ArrayList<Hero> heroes;
    private final ArrayList<Enemy> enemies;

    private final ArrayList<Projectile> projectiles; //hero, boss, and enemy projectiles

    private final ArrayList<Fruit> fruits;
    private final ArrayList<GameObject> toBeRemoved;
    private final ArrayList<Bubble> bubbles;


    private boolean readyToReset; //When hero dies
    private int live = 2; //hero life at the beginning
    private static MediaPlayer mediaPlayer; //for the background music
    private static AnimationTimer gameTimer; //used for update the position

    private static HighScore totalScore; //highscore for storing current player name and total score
    private Text totalScoreText ; //the total score shown on the right top corner of the screen
    private Text liveText; //the hero life shown on the left top corner of the screen
    private Text levelText; // the text to show the current level only shown on the screen two seconds
    private ArrayList<Text> scoreText; // an arraylist to store the score display when hero dies, enemy dies, and collect fruit.

    private int timer = 0; //timer for removing the display score when get food or kill enemy and the level display text
    private GameObjectFactory gameObjectFactory; // for creating game object

    public BossProgressController bossProgressController;//control the boss life
    public static AnchorPane root;//the pane for all the imageview and text on it

    private Scene scene;
    private GameView gameView;

    private Ellipse ellipse;//for the hero keycode w


    private String[] worldArray;//for storing different world
    private int currentWorldIndex = 0;
    private DifficultyState currentDifficultyState;//the difficulty of the game
    private GridPane bossPane; //the pane which has progress bar on it
    private AnchorPane pausePane;//the pane for the pause screen

    /**
     * The constructor of the game controller class.
     * @param gameView
     * @param playerName The name of the player.
     * @throws IOException
     */
    public GameController(GameView gameView, String playerName) throws IOException {
        //initializes the game
        staticObjects = new ArrayList<StaticObject>();
        heroes = new ArrayList<Hero>();
        enemies = new ArrayList<Enemy>();
        fruits = new ArrayList<Fruit>();
        toBeRemoved = new ArrayList<GameObject>();
        bubbles = new ArrayList<Bubble>();
        scoreText = new ArrayList<Text>();
        projectiles = new ArrayList<Projectile>();

        readyToReset = false;

        //this.stage = stage;
        this.gameView = gameView;
        String backgroundChoice = SettingController.backgroundChoice;
        root = gameView.createView(backgroundChoice);//initialize the anchor pane

        totalScore = new HighScore(playerName,0); //score starts from 0
        gameObjectFactory = new GameObjectFactory();


        currentDifficultyState = SettingController.getDifficulty();//get the difficulty that the player have selected
        worldArray = currentDifficultyState.getWorldArray(); //set the world according to different difficulty of game


        FXMLLoader fxmlLoader = new FXMLLoader();
        bossPane = fxmlLoader.load(getClass().getResource("/BossProgress.fxml").openStream());
        bossProgressController = fxmlLoader.getController();

        FXMLLoader fxmlLoader2 = new FXMLLoader();
        pausePane = fxmlLoader2.load(getClass().getResource("/Pause.fxml").openStream());


        initializeMusic();//initialize the background music
        initializeScene(); //initialize the scene
        startGame(worldArray[currentWorldIndex]);

        createGameLoop(scene);//animation timer in it for updating the position and update imageview

    }

    /**
     * Get the totalScore (name, score).
     * @return HighScore
     */
    public static HighScore getTotalScore(){
        return totalScore;
    }

    /**
     * CreateKeyListeners is used for detecting the key pressed by the player.<br>
     * Handle the hero movement. Move here to follow the mvc pattern.
     * @param scene
     */
    public void createKeyListeners(Scene scene){
        Hero hero = heroes.get(0); //heroes.get(0) since only one hero
        GameController gameController = this; //for line 196. Be the parameter for the bubble since cannot directly use this there.
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    //System.out.println("You press left");
                    hero.getHeroState().keyLeft(); //hero current state

                }
                else if (event.getCode() == KeyCode.RIGHT) {
                    //System.out.println("You press right");
                    hero.getHeroState().keyRight();

                }
                else if (event.getCode() == KeyCode.UP) {
                    //System.out.println("You press up");
                    hero.getHeroState().keyUp();

                }
                else if (event.getCode() == KeyCode.E) {
                    //System.out.println("You press E");
                    hero.getHeroState().keyE();

                }
                else if (event.getCode() == KeyCode.Q) {
                    //System.out.println("shield");
                    hero.getHeroState().keyQ();
                }
                else if (event.getCode() == KeyCode.SPACE) {
                    //System.out.println("Dash");
                    hero.getHeroState().keySpace();
                }
                else if (root.getChildren().contains(pausePane)){ //press again the q, the pause screen will disappear

                     if (event.getCode() == KeyCode.P) {
                         root.getChildren().remove(pausePane);
                         gameTimer.start();//continue the game
                    }
                }
                else if (event.getCode() == KeyCode.P) {

                    root.getChildren().add(pausePane);//pause screen show up
                    gameTimer.stop();//stop the game


                }
                else if (event.getCode() == KeyCode.W){
                    if (hero.getReadyToCharge()) {
                        addBubble(new Bubble(gameController, hero.x, hero.y));
                        SoundEffect.EXPLODE.setToLoud();
                        SoundEffect.EXPLODE.play();

                        hero.setChargeToFalse();//one time only for the bubble

                    }
                }

            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    //System.out.println("You release left");
                    hero.xAccel = 0;

                }
                else if (event.getCode() == KeyCode.RIGHT) {
                    //System.out.println("You release right");
                    hero.xAccel = 0;

                }
                else if (event.getCode() == KeyCode.E) {
                    //System.out.println("You not shoot");
                    hero.shootDelay = 0;

                }
                else if (event.getCode() == KeyCode.Q) {
                    //System.out.println("Shield");
                    hero.getHeroState().releaseQ();
                }
                else if (event.getCode() == KeyCode.SPACE) {

                    //System.out.println("No Dash");
                    hero.terminal_xVelocity = hero.WALK;
                }


            }
        });
    }
    /**
     * Set the enemy image view based on different direction according to different types of enemies <br>
     * less than 0 means left, greater than 0 means right
     * @param enemy
     */
    private void setEnemyImage(Enemy enemy){
        if (enemy.direction<0 && enemy.isBubbled==false && enemy instanceof NotShootEnemy){
            enemy.getImageView().setImage(new Image("MonsterLeft.png"));
        }
        else if (enemy.direction>0 && enemy.isBubbled==false && enemy instanceof NotShootEnemy){
            enemy.getImageView().setImage(new Image("MonsterRight.png"));
        }
        else if (enemy.direction<0 && enemy.isBubbled==false && enemy instanceof ShootEnemy){
            enemy.getImageView().setImage(new Image("ShootMonsterLeft.png"));
        }
        else if (enemy.direction>0 && enemy.isBubbled==false && enemy instanceof ShootEnemy){
            enemy.getImageView().setImage(new Image("ShootMonsterRight.png"));
        }
        else if (enemy.direction<0 && enemy.isBubbled==false && enemy instanceof BossEnemy){
            enemy.getImageView().setImage(new Image("BossLeft.png"));
        }
        else if (enemy.direction>0 && enemy.isBubbled==false && enemy instanceof BossEnemy){
            enemy.getImageView().setImage(new Image("BossRight.png"));
        }
    }
    /**
     * Set the position of the image view based on different game object<br>
     * Extract function from the update position.
     * @param gameObject
     */
    private void setImageViewPosition(GameObject gameObject){
        gameObject.getImageView().setLayoutX(gameObject.x);
        gameObject.getImageView().setLayoutY(gameObject.y);
    }

    /**
     * This is the method handles all the collision.<br>
     * Extract function from the update position
     */
    private void objectCollisionHandle(){
        staticObjectCollision(staticObjects);

        //All types of Enemies initiate collisions with Heroes
        for (Enemy enemy : enemies) {
            for (Hero hero : heroes) {
                enemy.collideWith(hero);
            }
        }

        // All types of projectiles collision with Heroes and Enemies
        for (Projectile projectile : projectiles){

            for (Hero hero : heroes) {
                projectile.collideWith(hero);
            }
            for (Enemy enemy : enemies) {
                projectile.collideWith(enemy);
            }
        }

        // Fruits intiate collisions with Heroes
        for (Fruit fruit : fruits) {
            for (Hero hero : heroes) {
                fruit.collideWith(hero);

            }
        }
        for (Bubble bubble : bubbles) {
            for (Enemy enemy : enemies) {
                bubble.collideWith(enemy);
            }
        }

    }
    /**
     * Handle the wallUnit, floorUnit, and CeilingUnit collision with other game object<br>
     * Follow single responsibility.
     * @param staticObjects
     */
    private void staticObjectCollision(ArrayList<StaticObject> staticObjects){
        for (StaticObject staticUnit:staticObjects){
            for (Hero hero : heroes) {
                staticUnit.collideWith(hero);
            }
            for (Enemy enemy : enemies) {
                staticUnit.collideWith(enemy);
                enemy.collideWith(staticUnit);
            }
            for (Fruit fruit : fruits) {
                staticUnit.collideWith(fruit);
            }
            for (Projectile projectile : projectiles){
                staticUnit.collideWith(projectile);
            }

        }
    }


    /**
     * updatePosition is used for update all the operation from different game object in the game
     * @throws IOException
     */
    private void updatePosition() throws IOException {
        //updates positions of everything on screen

        for (Hero hero : heroes) {
            hero.update();//update position
            setImageViewPosition(hero);
        }

        for (Enemy enemy : enemies) {
            enemy.update();
            setEnemyImage(enemy);
            if (enemy instanceof BossEnemy){
                if (bossProgressController.progressBar.getProgress()<0){ //if the progress less than zero, boss dies
                    root.getChildren().remove(bossPane);//remove the progress bar
                    SoundEffect.POP.play();
                    enemy.die();
                }
            }

            enemy.shootProjectile();//different enemy has different shoot behavior(strategy pattern)

            if(enemy.canRemove) {
                toBeRemoved.add(enemy);
                currentDifficultyState.fallFruit(root,this,enemy);
                //Different state has different image view and different point of fruit
            }
            setImageViewPosition(enemy);

        }

        for (Projectile projectile : projectiles){

            projectile.update();
            if (projectile.canRemove) {
                toBeRemoved.add(projectile);
            }
            setImageViewPosition(projectile);
        }

        for (Fruit fruit : fruits) {
            fruit.update();
            if (fruit.canRemove) {
                toBeRemoved.add(fruit);
            }

            setImageViewPosition(fruit); //set the fruit ImageView location after the fruit updates its location. This makes the fruit fall down.
        }
        for (Bubble bubble : bubbles) {

            root.getChildren().remove(ellipse);//this is for the hero bubble
            bubble.update();
            bubblePower(bubble);//draw the ellipse on the anchor pane

            if (bubble.canRemove) {
                toBeRemoved.add(bubble);
            }
        }

        // Colliding...
        // Units initiate collisions with Heroes, Enemies, and Fruits
        objectCollisionHandle();

        // Removing...
       for (GameObject obj : toBeRemoved) {
            remove(obj);

            root.getChildren().remove(obj.getImageView());//remove all the object that can be removed on the pane
        }
        toBeRemoved.removeAll(toBeRemoved);
        levelHandle();
    }

    /**
     * leveHandle is used for handling the switch scenes when player finish a level.<br>
     * If the player finish all the levels, a win screen when show up.
     * @throws IOException
     */
    public void levelHandle() throws IOException {
        if (live==0){
            gameTimer.stop();//game over
            totalScore.addHighScore(totalScore); //compared to the five highest history score
            App.setRoot("highScores");//switch the scene to highscore board

        }
        if (enemies.size()==0){
            if (currentWorldIndex==worldArray.length-1 && fruits.size()==0){ //if all the fruit is collected and the world level is the last level
                gameTimer.stop();
                totalScore.addHighScore(totalScore);//compared to the five highest history score
                App.setRoot("win");//switch to the player win scene
            }
            else if (currentWorldIndex!=worldArray.length-1 && fruits.size()==0){
                currentWorldIndex+=1; // move to the next level
                root.getChildren().clear();//clear everything on the pane
                startGame(worldArray[currentWorldIndex]);//move to the next level
            }

        }

        if (readyToReset && live>0) //hero dies and still has life
        {
            live-=1;
            startGame(worldArray[currentWorldIndex]);//start the current level again
        }

    }
    /**
     * Function get the text of the total score.
     * @return totalScoreText
     */
    public Text getTotalScoreText(){return totalScoreText;}

    /**
     * Function get the list of the temporary shown text
     * @return the arraylist of the temporary displayed text
     */
    public ArrayList<Text> getScoreText(){return scoreText;}

    /**
     * Function that add the ceiling unit to the arraylist of staticObjects
     * @param ceilingUnit
     */
    public void addCeilingUnit(CeilingUnit ceilingUnit) {
        staticObjects.add(ceilingUnit);
    }
    /**
     * Function that add the floor unit to the arraylist of staticObjects
     * @param floorUnit
     */
    public void addFloorUnit(FloorUnit floorUnit) {
        staticObjects.add(floorUnit);
    }
    /**
     * Function that add the ceiling unit to the arraylist of staticObjects
     * @param wallUnit
     */
    public void addWallUnit(WallUnit wallUnit) {
        staticObjects.add(wallUnit);
    }
    /**
     * Function that add the hero to the arraylist of heros although only one hero in the game
     * @param hero
     */
    public void addHero(Hero hero) {
        //adds a hero to the map
        heroes.add(hero);
    }
    /**
     * Function that add the enemy to the arraylist of enemies
     * @param enemy
     */
    public void addEnemy(Enemy enemy) {
        //adds a mook to the map
        enemies.add(enemy);
    }
    /**
     * Function that add the heroprojectile to the arraylist of projectiles <br>
     * and initialize the hero projectile imageview
     * @param heroProjectile
     */
    public void addHeroProjectile(HeroProjectile heroProjectile) {

        if (heroProjectile.getIsActive()) {
            ImageView heroProjectileImageView = heroProjectile.getImageView();
            setImageViewPosition(heroProjectile);//Initialize the hero projectile image view coordinates x and y
            root.getChildren().add(heroProjectileImageView);
        }
        projectiles.add(heroProjectile);
    }
    /**
     * Function that add the fruit to the arraylist of fruit.
     * @param fruit
     */
    public void addFruit(Fruit fruit) {
        //adds fruit on bubble pop
        fruits.add(fruit);
    }
    /**
     * Function that add the bubble to the arraylist of bubbles
     * @param bubble
     */
    public void addBubble(Bubble bubble) {
        //adds special bubble
        bubbles.add(bubble);
    }
    /**
     * Function that clears all the gameobject
     */
    private void clearContents() {
        //clears everything from the screen

        staticObjects.removeAll(staticObjects);
        heroes.removeAll(heroes);
        enemies.removeAll(enemies);
        fruits.removeAll(fruits);
        projectiles.removeAll(projectiles);
        bubbles.removeAll(bubbles);

    }
    /**
     * Function that removes a single object from the screen
     */
    private void remove(GameObject obj) {
        //removes a single object from the screen

        staticObjects.remove(obj);
        heroes.remove(obj);
        enemies.remove(obj);
        projectiles.remove(obj);
        fruits.remove(obj);
        bubbles.remove(obj);

    }
    /**
     * Function that reset the game
     */
    public void markToReset() {
        //sets boolean to make sure the world is ready to be reset
        readyToReset = true;

    }
    /**
     * Function that add the boss projectile to the arraylist projectiles and initialize its imageview.
     * @param bossProjectile
     */
    public void addBossProjectile(BossProjectile bossProjectile) {
        ImageView bossProjectileImageView = bossProjectile.getImageView();
        if(bossProjectile.direction<0){
            bossProjectileImageView.setLayoutX(bossProjectile.x-200);
            bossProjectileImageView.setLayoutY(bossProjectile.y-200);
            root.getChildren().add(bossProjectileImageView);
        }
        else if(bossProjectile.direction>0){
            bossProjectileImageView.setLayoutX(bossProjectile.x+200);
            bossProjectileImageView.setLayoutY(bossProjectile.y-200);
            root.getChildren().add(bossProjectileImageView);
        }

        //adds a projectile to where necessary
        projectiles.add(bossProjectile);
    }
    /**
     * Function that add enemy projectile to the projectiles and initialize its imageview
     * @param enemyProjectile
     */
    public void addEnemyProjectile(EnemyProjectile enemyProjectile) {
        if (enemyProjectile.getIsActive()) {
            ImageView enemyProjectileImageView = enemyProjectile.getImageView();
            setImageViewPosition(enemyProjectile); //Initialize the enemy projectile image view coordinates x and y
            root.getChildren().add(enemyProjectileImageView);
        }
        //adds a projectile to where necessary
        projectiles.add(enemyProjectile);
    }
    /**
     * Function that initialize the background music
     */
    private void initializeMusic(){

        Media h = new Media(getClass().getResource("/BackgroundMusic.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
    /**
     * Function that get the media player
     */
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
    /**
     * Function that initialize the text on the screen.<br>
     * Level text is the temporary text.<br>
     * Other two stays on the left and right top corner.
     */
    private void initializeText(){
        levelText = new Text("Level: "+0+(currentWorldIndex+1));//display the score text on the right corner of the screen
        levelText.setX(300);
        levelText.setY(50);
        levelText.setFont(Font.font("verdana", FontWeight.BOLD, 30));
        levelText.setFill(Color.WHITE);
        root.getChildren().add(levelText);

        totalScoreText = new Text("Score: "+totalScore.getScore());//display the score text on the right corner of the screen
        totalScoreText.setX(600);
        totalScoreText.setY(50);
        totalScoreText.setFont(Font.font("verdana", FontWeight.BOLD, 30));
        totalScoreText.setFill(Color.WHITE);
        root.getChildren().add(totalScoreText);

        liveText = new Text("Lives: "+live);//display the lives on the left corner of the screen
        liveText.setX(30);
        liveText.setY(50);
        liveText.setFont(Font.font("verdana", FontWeight.BOLD, 30));
        liveText.setFill(Color.WHITE);
        root.getChildren().add(liveText);
    }
    /**
     * Function that create the world. <br>
     * A gameobject factory is included for generating game object according to different signs.
     * @throws IOException
     */
    private void startGame(String world) throws IOException {

        gameObjectFactory.setCountOne(); //set the power up fruit number to be 1
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(world+".txt");
        Scanner scanner = new Scanner(input);
        clearContents();
        for (int row = 0; row < App.HEIGHT; row++) {
            String currentLine = scanner.next();
            for (int col = 0; col < App.WIDTH; col++) {
                char objectType = currentLine.charAt(col);

                GameObject gameObject = gameObjectFactory.getGameObject(objectType,this,col,row);
                if(gameObject!=null){
                    gameObject.getImageView().setLayoutX(gameObject.x); //move the drawOn method from the model to controller
                    gameObject.getImageView().setLayoutY(gameObject.y);
                    root.getChildren().add(gameObject.getImageView());//put on the pane
                }

            }
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }
        if (world=="worldBoss") {
            root.getChildren().add(bossPane);//add the progress bar
        }

        scanner.close();
        initializeText();//score and life
        readyToReset = false;
    }
    /**
     * GetReadyToSet
     * @return readyToReset
     */
    public boolean getReadyToReset(){return readyToReset;}

    /**
     * Function that get the hero in the game
     * @return hero
     */
    public Hero getHero(){return heroes.get(0);}

    /**
     * Function that add one hero life
     */
    public void addOneLive(){live+=1;}

    /**
     * Function that updates the text live on the screen
     */
    public void updateLiveText(){liveText.setText("Lives: "+live);}
    /**
     * Function that creates ellipse for hero super power
     * @param bubble
     */
    private void bubblePower(Bubble bubble){
        ellipse = new Ellipse();
        if (bubble.width <= 2500) {
            ellipse.setFill(new Color(255/255.0, 204/255.0, 102/255.0, 0.5));//color
        }
        else {
            ellipse.setFill(new Color(255/255.0, 204/255.0, 102/255.0, 0));
        }
        //set coordinates
        ellipse.setCenterX(bubble.x);
        ellipse.setCenterY(bubble.y);
        // set radius
        ellipse.setRadiusX(bubble.width);
        ellipse.setRadiusY(bubble.height);
        root.getChildren().add(ellipse);
    }
    /**
     * Function that get anaimation timer
     * @return gameTimer
     */
    public static AnimationTimer getAnimationTimer(){return gameTimer;}

    /**
     * Function that initialize the scene
     * @return gameTimer
     */
    private void initializeScene(){

        scene = App.getScene();
        scene.setRoot(root);

    }
    /**
     * CreateGameLoop for the animation timer. Every two seconds it will check to remove the <br>
     * temporary display text on the screen, and the temporary level text.
     * @param scene
     */
    private void createGameLoop(Scene scene) {
        gameTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                try {
                    updatePosition();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (timer==120){ // decrease or increase score text only shows 2 seconds and the level text only show 2 seconds
                    for (int i=0; i<scoreText.size();i++){ //clear all the decrease and increase  score text shown on the screen
                        root.getChildren().remove(scoreText.get(i));

                    }
                    root.getChildren().remove(levelText);//remove the level text
                    timer = 0; //reset timer to zero
                }
                timer+=1;//timer add one for the temporary displaying text on the screen
                createKeyListeners(scene);
            }
        };
        gameTimer.start();

    }
}
