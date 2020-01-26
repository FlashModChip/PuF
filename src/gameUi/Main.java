package gameUi;
import components.PositionComponent;
import entities.Enemy;
import entities.Item;
import entities.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import level.LevelData;
import level.LevelGenerator;
import level.LevelToUi;
import level.MapNavigator;
import level.RoomData;
import settings.Settings;
import systems.SystemManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class Main extends Application {

    private static Stage guiStage;
    private static Scene gameScene;
    private SystemManager systemManager;

    public static Stage getGuiStage() {
        return guiStage;
    }
    public static Scene getGameScene() {
        return gameScene;
    }

    // TilesSize + PlayerSize & WindowSize
    static Settings settings = Settings.getInstance();

    // store key input in a hashmap
    public static HashMap<KeyCode,Boolean> keyInput = new HashMap<>();
    //UNSCHÖN
    public static ArrayList<Bounds> colliderWallMap = new ArrayList<>();
    public static ArrayList<Bounds> colliderEnemiesMap = new ArrayList<>();
    public static ArrayList<Bounds> colliderDoorMap = new ArrayList<>();

    public static LevelGenerator lol = new LevelGenerator();
    //public static LevelData lvl = lol.generateLevel(1, false);
    
    public MapNavigator nav = MapNavigator.getInstance();   

    public static LevelToUi map;

    //canvas test for drawing map (for better performance?)
    private static Canvas canvas = new Canvas(settings.getWindowWidth(), settings.getWindowHeight());
    private static GraphicsContext gc = canvas.getGraphicsContext2D();

    public static GraphicsContext getGc() {
        return gc;
    }
    
    public static LevelToUi getMap() {
    	
        return map;
    }
    
    public static void updateMap() {
    	
    	map.tilesRenderer(gc);
    	
    	//debug_pane.getChildren().addAll(map.interactiveRectLayer());
    	
    	colliderWallMap = map.boundsWallRectLayer();
        colliderEnemiesMap = map.boundsEnemiesRectLayer();
        colliderDoorMap = map.boundsDoorRectLayer();
        
    }

    //Player
    static Player player = new Player(200.0,300.0);
    //Enemy enemy = new Enemy(400.0, 300.0);
    //Enemy enemy2 = new Enemy(450.0, 300.0);
    Item item1 = new Item(100,250);

    public static Pane root = new Pane();
    public static Pane debug_pane;

    public static Player getPlayer() {
        return player;
    }

    /**
     * init ressources
     */
    @Override
    public void init() {
        System.out.println("Running...");
        nav.changeLevel(lol.generateLevel(1, true));

        nav.setStartingRoom();

        map = new LevelToUi();
        colliderWallMap = map.boundsWallRectLayer();
        colliderEnemiesMap = map.boundsEnemiesRectLayer();
        colliderDoorMap = map.boundsDoorRectLayer();

        // init & run the SystemManager
        systemManager = new SystemManager();
        systemManager.init();       

       
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

//        System.out.println("first Room entry UUID: " + firstRoom);

        guiStage = primaryStage;
//        map.handleLevelGrid();
        map.tilesRenderer(gc);
        

        debug_pane = new Pane();
        
        //debug_pane.getChildren().addAll(map.interactiveRectLayer());
        
        colliderWallMap = map.boundsWallRectLayer();
        colliderEnemiesMap = map.boundsEnemiesRectLayer();
        colliderDoorMap = map.boundsDoorRectLayer();


        Pane hud = new Pane();
        hud = FXMLLoader.load(getClass().getResource("../resources/view/hud.fxml"));
        hud.setLayoutY(400);


        //Layout gameScene
        Group rootGame = new Group();
        rootGame.getChildren().addAll(canvas, debug_pane,hud, root);
        gameScene = new Scene(rootGame, settings.getWindowWidth(), settings.getWindowHeight());



        // capture user input into buffer
        gameScene.setOnKeyPressed(event-> keyInput.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keyInput.put(event.getCode(), false));

        // game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                update();
            }
        };
        timer.start();


// ++++++  UI Schnellstart ins Spiel +++++++
        guiStage = primaryStage;
        guiStage.setTitle("Darkest Crawler");
        guiStage.setScene(gameScene);
        guiStage.show();

// ++++++  UI Start mit Intro +++++++
//        guiStage = primaryStage;
//        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/main.fxml"));
//        guiStage.setTitle("Darkest Crawler");
//        guiStage.setScene(new Scene(root, 600, 400));
//        guiStage.show();

// ++++++  UI Start mit Intro +++++++
//        guiStage = primaryStage;
//        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/main.fxml"));
//        guiStage.setTitle("Darkest Crawler");
//        guiStage.setScene(new Scene(root, 600, 400));
//        guiStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * main update tick
     */
    private void update() {
        // run the SystemManager
        systemManager.update();

        // move camera to player position
        Point2D playerPosition = (Point2D) player.getComponent(PositionComponent.class).getValue();
       
        //prevents too fast level changes
        nav.unlock();
        
    }

    /**
     * helper function to parse key events
     *
     * @param key
     *      keycode
     * @return
     *      boolean: is key pressed
     */
    private boolean isPressed(KeyCode key){
        return keyInput.getOrDefault(key,false);
    }

    // FXMLLoader help
    public static Node loadFXML(String fxmlFilename) {
        try {
            return FXMLLoader.load(Main.class.getClassLoader().getResource(fxmlFilename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
