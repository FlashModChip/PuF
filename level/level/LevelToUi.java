package level;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class LevelToUi {

    private int roomLengthX;
    private int roomLengthY;
    private int[][] map;

    private int rectLength;


    /// </summary>
    public LevelToUi(int[][] mapCords, int rectSizeX, int rectSizeY, int rectX) {
        this.roomLengthX = rectSizeX;
        this.roomLengthY = rectSizeY;
        this.rectLength = rectX;
        this.map = mapCords;
    }

    //Interactive layer
    public ArrayList interactiveRectLayer() {
        Rectangle rect = null;

        ArrayList<Rectangle> mapTheRealOne = new ArrayList<>();
        for (int i = 0; i < roomLengthY; i++) {
            for (int j = 0; j < roomLengthX; j++) {

                if (map[i][j] == 255) {
                    // mapTheRealOne.add(rectGenerator(rect,Color.BLACK,j,i));
                } else if (map[i][j] == 100) {
                    // mapTheRealOne.add(rectGenerator(rect,Color.GREY,j,i));
                } else if (map[i][j] == 150) {
                    mapTheRealOne.add(rectGenerator(rect, Color.BLUE, j, i));
                } else if (map[i][j] == 20) {
                    mapTheRealOne.add(rectGenerator(rect, Color.RED, j, i));
                }
            }

        }
        System.out.println(mapTheRealOne);
        return mapTheRealOne;
    }

    //Map layer
    public GraphicsContext tilesRenderer(GraphicsContext graphicsContext, Image img) {

        for (int i = 0; i < roomLengthY; i++) {
            for (int j = 0; j < roomLengthX; j++) {
                //wall
                if (map[i][j] == 255) {
                    wallGenerator(graphicsContext, i, j, img);

                    //floor
                } else if (map[i][j] == 100) {
                    floorGenerator(graphicsContext, i, j, img);

                    //doors
                } else if (map[i][j] == 150) {
                    doorGenerator(graphicsContext, i, j, img);

                    //items?
                } else if (map[i][j] == 20) {
                    itemGenerator(graphicsContext, i, j, img);

                }
            }
        }
        //System.out.println(mapTheRealOne);
        return graphicsContext;
    }


    //drawImage( Imagesource, xCordFromImage, yCordFromImage, widthFromImage, heightFromImage, xDrawCord, yDrawCord, width, height)
    // wall generator
    private GraphicsContext wallGenerator(GraphicsContext gc, int i, int j, Image img) {
        try {
            if ((map[i][j + 1] == 255 || map[i][j + 1] == 100) && (map[i][j - 1] != 255 || map[i][j - 1] != 100) ||
                    (map[i][j + 1] != 255 || map[i][j + 1] != 100) && (map[i][j - 1] == 255 || map[i][j - 1] == 100)) {

                gc.drawImage(img, 48, 64, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            gc.drawImage(img, 96, 64, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        }
        return gc;
    }

    // floor generator
    private GraphicsContext floorGenerator(GraphicsContext gc, int i, int j, Image img) {
        if (map[i - 1][j] == 255) {
            gc.drawImage(img, 48, 80, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        } else if (map[i - 1][j] == 150) {
            gc.drawImage(img, 0, 192, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        } else {
            gc.drawImage(img, 0, 112, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        }
        return gc;
    }

    // door generator
    private GraphicsContext doorGenerator(GraphicsContext gc, int i, int j, Image img) {
        gc.drawImage(img, 0, 176, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        return gc;
    }

    // ?item or whatever generator
    private GraphicsContext itemGenerator(GraphicsContext gc, int i, int j, Image img) {
        gc.drawImage(img, 0, 112, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
        gc.drawImage(img, 112, 240, 16, 16, j * rectLength, i * rectLength, rectLength, rectLength);
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
