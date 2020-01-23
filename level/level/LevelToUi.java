package level;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import gameUi.Main;

/**
 * class for drawing the level using level data and a tile map
 * 
 * @author Roman² :-)
 */
public class LevelToUi {

    private int rectLength = 40;

    private int roomWidth, roomHigh;

    //private LevelData levelData;
    //private int[][] levelGrid;
    private MapNavigator navi;

    private TileMap TileMap;
    
    
    public LevelToUi() {
    	navi = MapNavigator.getInstance();       
        TileMap = new TileMap();
        roomWidth = navi.getLevel().getRoomSizeY();
        roomHigh = navi.getLevel().getRoomSizeX();
    }

    // Interactive layer
    public ArrayList interactiveRectLayer() {
        Rectangle rect = null;

        ArrayList<Rectangle> mapTheRealOne = new ArrayList<>();
        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                if (navi.getCurrentRoom().getRoomData()[i][j] == TileCode.wall) {
                    mapTheRealOne.add(rectGenerator(rect, TileCode.wallRGB, i, j));
                } else if (navi.getCurrentRoom().getRoomData()[i][j] == TileCode.floor) {
                    mapTheRealOne.add(rectGenerator(rect, TileCode.floorRGB, i, j));
                } else if (navi.getCurrentRoom().getRoomData()[i][j] == TileCode.door) {
                    mapTheRealOne.add(rectGenerator(rect, TileCode.doorRGB, i, j));
                } else if (navi.getCurrentRoom().getRoomData()[i][j] == TileCode.enemy) {
                    mapTheRealOne.add(rectGenerator(rect, TileCode.enemyRGB, i, j));
                }
                // More tiles possible
            }
        }
        System.out.println(mapTheRealOne);
        return mapTheRealOne;
    }

    // Enemies als Hashmap
    public ArrayList<Bounds> boundsWallRectLayer() {
        ArrayList<Bounds> mapBounds = new ArrayList<>();
        Rectangle rect = null;

        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                if (navi.getCurrentRoom().getRoomData()[i][j] == 255) {
                    mapBounds.add(rectGenerator(rect, Color.BLACK, i, j).getBoundsInParent());
                }
            }

        }

        return mapBounds;
    }

    // Enemies als Hashmap
    public ArrayList<Bounds> boundsEnemiesRectLayer() {
        ArrayList<Bounds> mapBounds = new ArrayList<>();
        Rectangle rect = null;

        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                if (navi.getCurrentRoom().getRoomData()[i][j]== 20) {
                    mapBounds.add(rectGenerator(rect, Color.BLACK, i, j).getBoundsInParent());
                }
            }
        }
        return mapBounds;
    }

    // Enemies als Hashmap
    public ArrayList<Bounds> boundsDoorRectLayer() {
        ArrayList<Bounds> mapBounds = new ArrayList<>();
        Rectangle rect = null;

        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                if (navi.getCurrentRoom().getRoomData()[i][j] == 150) {
                    mapBounds.add(rectGenerator(rect, Color.BLACK, i, j).getBoundsInParent());
                }
            }
        }
        return mapBounds;
    }

    // Map layer
    public GraphicsContext tilesRenderer(GraphicsContext graphicsContext) {

        for (int i = 0; i < roomHigh; i++) {
            for (int j = 0; j < roomWidth; j++) {

                // wall
                if (navi.getCurrentRoom().getRoomData()[i][j] == TileCode.wall) {
                    tileGenerator(graphicsContext, TileType.WALL, j, i);
                    // floor
                } else if (navi.getCurrentRoom().getRoomData()[i][j] == TileCode.floor) {
                    tileGenerator(graphicsContext, TileType.FLOOR, j, i);
                    // doors
                } else if (navi.getCurrentRoom().getRoomData()[i][j] == TileCode.door) {
                    tileGenerator(graphicsContext, TileType.DOOR, j, i);
                    // items ?
                } else if (navi.getCurrentRoom().getRoomData()[i][j] == TileCode.enemy) {
                    tileGenerator(graphicsContext, TileType.ENEMY, j, i);
                }
                // more tiles possible
            }
        }
        // System.out.println(mapTheRealOne);
        return graphicsContext;
    }

    private GraphicsContext tileGenerator(GraphicsContext gc, TileType type, int i, int j) {

        Point2D tile = this.TileMap.getTile(type);
        int size = this.TileMap.getTileSize();

        gc.drawImage(this.TileMap.getTileImage(), tile.getX(), tile.getY(), size, size, j * rectLength, i * rectLength,
                rectLength, rectLength);

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
    
    
    public MapNavigator getMapNavigator() {
    	return navi;    
    }

}
