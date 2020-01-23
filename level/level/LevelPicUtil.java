package level;

import java.io.File;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/// <summary>
/// Reading/saving level data from/to image files
/// </summary>
public class LevelPicUtil {
    private LevelPicUtil() {

    }

    /**
     * Method reading PNG image and generates tile code
     * 
     * @param path
     * @param sizex
     * @param sizey
     * @return
     */
    public static int[][] getLevelDatafromPNG(String path, int sizex, int sizey) {
        File file = new File("level/roomTemplates/roomTemplate" + randomNumber(1, 9) + ".png");
        Image image = new Image(file.toURI().toString());

        PixelReader pixelReader = image.getPixelReader();

        int imgWidth = (int) image.getWidth();
        int imgHeight = (int) image.getHeight();

        // Size not tile size
        if (imgWidth != sizex || imgHeight != sizey) {
            // probably should be an exception ... nah
            return null;
        }

        int[][] tmp = new int[imgWidth][imgHeight];

        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                Color c = pixelReader.getColor(x, y);

                if (c.equals(TileCode.wallRGB)) {
                    tmp[x][y] = TileCode.wall;
                } else if (c.equals(TileCode.enemyRGB)) {
                    tmp[x][y] = TileCode.enemy;
                } else if (c.equals(TileCode.doorRGB)) {
                    tmp[x][y] = TileCode.door;
                } else if (c.equals(TileCode.floorRGB)) {
                    tmp[x][y] = TileCode.floor;
                } else if (c.equals(TileCode.itemRGB)) {
                    tmp[x][y] = TileCode.item;
                } else if (c.equals(TileCode.spawnRGB)) {
                    tmp[x][y] = TileCode.spawn;
                } else if (c.equals(TileCode.goalRGB)) {
                    tmp[x][y] = TileCode.goal;
                }
                /// ... more tile codes and values possible
            }
        }

        return tmp;
    }

    /**
     * Currently unnecessary - we know how to use paint :-)
     * 
     * @param levelData
     * @param dir
     */
    public static void setLevelDataToPNG(byte[][] levelData, String dir) {
        // ToDo
    }

    /**
     * Generates random integer between 2 values, because c# can do that and java
     * can't XD
     * 
     * @param low
     * @param high
     * @return
     */
    private static int randomNumber(int low, int high) {

        Random ran = new Random();

        return ran.nextInt(high - low) + low;
    }
}
