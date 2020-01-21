package level;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class LevelToUi {

    int room = 20;

    private String tilesMapSrc = "level/ressources/tiles0.png";
    private Image tilesGraphic = new Image(tilesMapSrc);

    private int roomWidth =10;
    private int roomHigh = 20;
    private int rectLength = 40;

    private static int[][] levelGrid;



    public LevelToUi() {

    }

//    /**
//     * @param levelGrid
//     * @param roomWidth
//     * @param roomHigh
//     * @param rectX
//     */
    /// </summary>
    public LevelToUi(int[][] levelGrid) {
//        this.roomLengthX = rectSizeX;
//        this.roomLengthY = rectSizeY;
//        this.rectLength = rectX;
        this.levelGrid = levelGrid;
    }


    //Interactive layer
    public ArrayList interactiveRectLayer() {
        Rectangle rect = null;

        ArrayList<Rectangle> mapTheRealOne = new ArrayList<>();
        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                if (levelGrid[i][j] == 255) {
                    mapTheRealOne.add(rectGenerator(rect,Color.BLACK,i,j));
                } else if (levelGrid[i][j] == 100) {
                    mapTheRealOne.add(rectGenerator(rect,Color.GREY,i,j));
                } else if (levelGrid[i][j] == 150) {
                    mapTheRealOne.add(rectGenerator(rect, Color.BLUE, i,j));
                } else if (levelGrid[i][j] == 20) {
                    mapTheRealOne.add(rectGenerator(rect, Color.RED, i,j));
                }
            }

        }
        System.out.println(mapTheRealOne);
        return mapTheRealOne;
    }

    //Enemies als Hashmap
    public ArrayList<Bounds> boundsWallRectLayer(){
        ArrayList<Bounds> mapBounds = new ArrayList<>();
        Rectangle rect = null;

        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                if (levelGrid[i][j] == 255) {
                    mapBounds.add(rectGenerator(rect, Color.BLACK, i,j).getBoundsInParent());
                }
            }

        }

        return mapBounds;
    }

    //Enemies als Hashmap
    public ArrayList<Bounds> boundsEnemiesRectLayer(){
        ArrayList<Bounds> mapBounds = new ArrayList<>();
        Rectangle rect = null;

        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                if (levelGrid[i][j] == 20) {
                    mapBounds.add(rectGenerator(rect, Color.BLACK, i,j).getBoundsInParent());
                }
            }
        }
        return mapBounds;
    }

    //Enemies als Hashmap
    public ArrayList<Bounds> boundsDoorRectLayer(){
        ArrayList<Bounds> mapBounds = new ArrayList<>();
        Rectangle rect = null;

        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                if (levelGrid[i][j] == 150) {
                    mapBounds.add(rectGenerator(rect, Color.BLACK, i,j).getBoundsInParent());
                }
            }
        }
        return mapBounds;
    }




    //Map layer
    public GraphicsContext tilesRenderer(GraphicsContext graphicsContext) {

        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                //wall
                if (levelGrid[i][j] == 255) {
                    wallGenerator(graphicsContext, j,i, tilesGraphic);

                    //floor
                } else if (levelGrid[i][j] == 100) {
                    floorGenerator(graphicsContext, j,i, tilesGraphic);

                    //doors
                } else if (levelGrid[i][j] == 150) {
                    doorGenerator(graphicsContext, j,i, tilesGraphic);

                    //items?
                } else if (levelGrid[i][j] == 20) {
                    itemGenerator(graphicsContext, j,i, tilesGraphic);

                }
            }
        }
        //System.out.println(mapTheRealOne);
        return graphicsContext;
    }


    //drawImage( Imagesource, xCordFromImage, yCordFromImage, widthFromImage, heightFromImage, xDrawCord, yDrawCord, width, height)
    // wall generator
    private GraphicsContext wallGenerator(GraphicsContext gc, int i, int j, Image img) {
        gc.drawImage(img, 0, 16, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        return gc;
    }

    // floor generator
    private GraphicsContext floorGenerator(GraphicsContext gc, int i, int j, Image img) {
        gc.drawImage(img, 16, 0, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        return gc;
    }

    // door generator
    private GraphicsContext doorGenerator(GraphicsContext gc, int i, int j, Image img) {
//        gc.drawImage(img, 0, 176, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        gc.drawImage(img, 80, 0, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        return gc;
    }

    // ?item or whatever generator
    private GraphicsContext itemGenerator(GraphicsContext gc, int i, int j, Image img) {
        gc.drawImage(img, 16, 16, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        return gc;
    }

    // interactive rectangles
    private Rectangle rectGenerator(Rectangle rect, Color color, int xCord, int yCord) {
        rect = new Rectangle();
        rect.setHeight(rectLength);
        rect.setWidth(rectLength);
        rect.setStroke(color);
        rect.setFill(Color.TRANSPARENT);
        rect.setX(rectLength * xCord);
        rect.setY(rectLength * yCord);
        rect.setOnMouseClicked(e -> {
            System.out.println("thats a eventRect");
        });
        return rect;
    }

}
