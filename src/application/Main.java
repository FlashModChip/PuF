package application;

import components.PositionComponent;
import components.Sprite;
import entities.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import level.LevelGenerator;
import level.LevelToUi;
import settings.Settings;
import systems.SystemManager;

import java.util.HashMap;

import static javafx.scene.transform.Transform.translate;


public class Main extends Application {

    Stage window;
    Scene startScene, gameScene;
    private SystemManager systemManager;
    Settings settings = Settings.getInstance();
    // store key input in a hashmap
    public static HashMap<KeyCode,Boolean> keyInput = new HashMap<>();

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
    LevelToUi map = new LevelToUi(testMap, lol.getRoomTileSizeX(), lol.getRoomTileSizeY(), STAGE_WIDTH / 20);


    //canvas test for drawing map (for better performance?)
    String tilesMapSrc = "level/ressources/tilesmap.png";
    Image image = new Image(tilesMapSrc);
    Canvas canvas = new Canvas(STAGE_WIDTH, STAGE_WIDTH / 2);
    GraphicsContext gc = canvas.getGraphicsContext2D();

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
    public void start(Stage primaryStage) {


        window = primaryStage;

        map.tilesRenderer(gc, image);

        //Button Start
        Label startLabel = new Label("clicke here to play the game");
        Button startButton = new Button("START");
        startButton.setOnAction(e -> window.setScene(gameScene));

        //Layout startScene
        VBox startLayout = new VBox(20);
        startLayout.getChildren().addAll(startLabel, startButton);
        startScene = new Scene(startLayout, STAGE_WIDTH, 600);

        Pane overlay = new Pane();
        overlay.getChildren().addAll(map.interactiveRectLayer());


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

        window.setScene(startScene);
        window.setTitle("Test run");
        window.centerOnScreen();
        window.show();
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

       // ((Sprite) player.getComponent(Sprite.class)).translateX(playerPosition.getX());


        // reset camera
        if(isPressed(KeyCode.R)){
            System.out.println("hello" + playerPosition);
        }
        System.out.println("hello%%%%%%" + playerPosition);

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

//Roman Boer

    //Test
    // gc.drawImage(image,10,10);
    //	lol.generateLevel(1, false);

    /*
     * BorderPane root = new BorderPane(); Scene scene = new Scene(root,400,400);
     * scene.getStylesheets().add(getClass().getResource("application.css").
     * toExternalForm()); primaryStage.setScene(scene); primaryStage.show();
     */

}
