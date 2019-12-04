package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import level.LevelGenerator;
import level.LevelToUi;


public class Main extends Application {

    Stage window;
    Scene startScene, gameScene;

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
        rootGame.getChildren().addAll(canvas, overlay);
        gameScene = new Scene(rootGame, STAGE_WIDTH, 600);

        window.setScene(startScene);
        window.setTitle("Test run");
        window.centerOnScreen();
        window.show();
    }

    public static void main(String[] args) {
        launch(args);

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
