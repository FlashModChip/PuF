package gameUi;
import components.PositionComponent;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import level.LevelGenerator;
import level.LevelToUi;
import settings.Settings;
import systems.SystemManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


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


    Settings settings = Settings.getInstance();
    // store key input in a hashmap
    public static HashMap<KeyCode,Boolean> keyInput = new HashMap<>();
    //UNSCHÖN
    public static ArrayList<Bounds> colliderWallMap = new ArrayList<>();
    public static ArrayList<Bounds> colliderEnemiesMap = new ArrayList<>();
    public static ArrayList<Bounds> colliderDoorMap = new ArrayList<>();
    int STAGE_WIDTH = 900;


    // Test 2d intArray
    int[][] testMap = {
            {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 150, 255, 255, 255, 255, 255, 255, 255, 255, 255},
            {255, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 255},
            {255, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 255},
            {255, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 255},
            {255, 100, 100, 100, 20, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 255},
            {150, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 150},
            {255, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 255},
            {255, 100, 20, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 255},
            {255, 100, 100, 100, 100, 20, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 255},
            {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 150, 255, 255, 255, 255, 255, 255, 255, 255, 255}
    };

   LevelGenerator lol = new LevelGenerator();
   public LevelToUi map = new LevelToUi(testMap, lol.getRoomTileSizeX(), lol.getRoomTileSizeY(), STAGE_WIDTH / 20);


    //canvas test for drawing map (for better performance?)
    String tilesMapSrc = "level/ressources/tilesmap.png";
    Image image = new Image(tilesMapSrc);
    Canvas canvas = new Canvas(STAGE_WIDTH, STAGE_WIDTH / 2);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    //Player
    Player player = new Player(300.0,200.0);
    public static Pane root = new Pane();

    /**
     * init ressources
     */
    @Override
    public void init() {
        System.out.println("Running...");

        // init & run the SystemManager
        systemManager = new SystemManager();
        systemManager.init();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        guiStage = primaryStage;

        map.tilesRenderer(gc, image);

        Pane overlay = new Pane();
        overlay.getChildren().addAll(map.interactiveRectLayer());
        colliderWallMap = map.boundsWallRectLayer();
        colliderEnemiesMap = map.boundsEnemiesRectLayer();
        colliderDoorMap = map.boundsDoorRectLayer();

        //Layout gameScene
        Group rootGame = new Group();
        rootGame.getChildren().addAll(canvas, overlay, root);
        gameScene = new Scene(rootGame, STAGE_WIDTH, 600);

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
//        guiStage = primaryStage;
//        guiStage.setTitle("Darkest Crawler");
//        guiStage.setScene(gameScene);
//        guiStage.show();

// ++++++  UI Start mit Intro +++++++
        guiStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/main.fxml"));
        guiStage.setTitle("Darkest Crawler");
        guiStage.setScene(new Scene(root, 600, 400));
        guiStage.show();
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
