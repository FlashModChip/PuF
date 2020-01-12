package gameUi.controller;

import components.PositionComponent;
import entities.Player;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import level.LevelGenerator;
import level.LevelToUi;
import settings.Settings;
import systems.SystemManager;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {

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

    @FXML
    private Group gameGroup;

    @FXML
    private Canvas mapCanvas;

    @FXML
    BorderPane borderPane;

    @FXML
    private Pane interactivLayer;

    private SystemManager systemManager;
    Settings settings = Settings.getInstance();

    // store key input in a hashmap
    public static HashMap<KeyCode,Boolean> keyInput = new HashMap<>();
    //UNSCHÖN
    public static ArrayList<Bounds> colliderWallMap = new ArrayList<>();
    public static ArrayList<Bounds> colliderEnemiesMap = new ArrayList<>();
    public static ArrayList<Bounds> colliderDoorMap = new ArrayList<>();
    Player player = new Player(300.0,200.0);

    LevelGenerator lol = new LevelGenerator();
    public LevelToUi map = new LevelToUi(testMap, lol.getRoomTileSizeX(), lol.getRoomTileSizeY(), 900 / 20);

    String tilesMapSrc = "level/ressources/tilesmap.png";
    Image image = new Image(tilesMapSrc);
    public static Pane root = new Pane();



    @FXML
    protected void initialize() {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        map.tilesRenderer(gc, image);
        interactivLayer.getChildren().addAll(map.interactiveRectLayer());

        System.out.println("Running...");
        // init & run the SystemManager
        systemManager = new SystemManager();
        systemManager.init();

        gameGroup.getChildren().addAll(root);
        // capture user input into buffer
//        Start.getGuiStage().getScene().setOnKeyPressed(event-> keyInput.put(event.getCode(), true));
//        Start.getGuiStage().getScene().setOnKeyReleased(event -> keyInput.put(event.getCode(), false));

        // game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    /**
     * main update tick
     */
    private void update() {
        // run the SystemManager
        systemManager.update();

        // move camera to player position
        Point2D playerPosition = (Point2D) player.getComponent(PositionComponent.class).getValue();

        // ((Sprite) player.getComponent(Sprite.class)).translateX(playerPosition.getX());


        // reset camera

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

}
