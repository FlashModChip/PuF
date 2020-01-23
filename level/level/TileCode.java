package level;

import javafx.scene.paint.Color;

/**
 * class with tile codes (color for streaming from png and int value for
 * internal use)
 * 
 * @author Roman
 */
public final class TileCode {

    public static final int wall = 255;
    public static final Color wallRGB = Color.BLACK;

    public static final int floor = 100;
    public static final Color floorRGB = Color.WHITE;

    public static final int door = 150;
    public static final Color doorRGB = Color.BLUE;

    public static final int enemy = 20;
    public static final Color enemyRGB = Color.RED;

    public static final int item = 10;
    public static final Color itemRGB = Color.YELLOW;

    public static final int spawn = 33;
    public static final Color spawnRGB = Color.GREEN;

    public static final int goal = 133;
    public static final Color goalRGB = Color.BLUE;

    // if adding another tile code please consider adding the type in TileType
    // aswell

    private TileCode() {
    }

}
