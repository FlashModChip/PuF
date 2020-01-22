package level;

import java.util.HashMap;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * class for wrapping an image and accessing its tile size and tile type
 * coordinates
 * 
 * @author Roman
 */
public class TileMap {

    private Image tilesGraphic;
    private int mapTileSize;

    private HashMap<TileType, Point2D> TileCoordinates;

    
    /**
     * Standard constructor
     */
    public TileMap () 
    {
        this.tilesGraphic = new Image("level/ressources/tiles0.png");
        this.mapTileSize = 16; 
        
        TileCoordinates = new HashMap<TileType,Point2D>();

        //Add Tile types and their coordinates in the image here
        TileCoordinates.put(TileType.WALL, new Point2D(0,16));
        TileCoordinates.put(TileType.FLOOR, new Point2D(16,0));
        TileCoordinates.put(TileType.DOOR, new Point2D(80,0));
        TileCoordinates.put(TileType.ITEM, new Point2D(16,16));   
     
    }

    
    /**
     * param. constructor
     * @param path file path
     * @param mptlsz tile size of each specific tile
     * @param tilecoord coordinates of each tile type
     */
    public TileMap(String path, int mptlsz, HashMap<TileType, Point2D> tilecoord) {
        this.tilesGraphic = new Image(path);
        this.mapTileSize = mptlsz;
        TileCoordinates = tilecoord;
    }

    
    /**
     * Reference to tile map image file
     * @return
     */
    public Image getTileImage() {
        return tilesGraphic;
    }

    /**
     * tile size of a  single tile
     * @return
     */
    public int getTileSize() {
        return mapTileSize;
    }

    /**
     * gets coordinates of a specific tile type on the tile map
     * @param type
     * @return
     */
    public Point2D getTile(TileType type) {
        return TileCoordinates.get(type);
    }
}
