package level;

import java.util.HashMap;
import java.util.UUID;

/// <summary>
/// Contains level data (all rooms and some meta informations, like lvl number)
/// </summary>
public class LevelData {

    // List of individual room data
    private HashMap<UUID, RoomData> roomList;

    // 2 dimensional array of rooms (via ID reference)
    private UUID[][] roomGrid;

    // 2 dimensional array of level data
    private int[][] levelgrid;

    private int lvlNumber, roomSizeX, roomSizeY;

    /**
     * @param lvlrm
     * @param roomgrd
     * @param lvlgrd
     * @param lvlnum
     */
    public LevelData(HashMap<UUID, RoomData> lvlrm, UUID[][] roomgrd, int[][] lvlgrd, int lvlnum, int rmSizeX,
            int rmSizeY) {
        roomList = lvlrm;
        roomGrid = roomgrd;
        levelgrid = lvlgrd;
        lvlNumber = lvlnum;
        roomSizeX = rmSizeX;
        roomSizeY = rmSizeY;
    }

    /**
     * @return
     */
    public HashMap<UUID, RoomData> getLevelRoomList() {
        return roomList;
    }

    /**
     * @return
     */
    public UUID[][] getLevelRoomGrid() {
        return roomGrid;
    }

    /**
     * @return
     */
    public int[][] getLevelGrid() {
        return levelgrid;
    }

    /**
     * @return
     */
    public int getLevelNumber() {
        return lvlNumber;
    }

    /**
     * @return
     */
    public int getRoomSizeX() {
        return roomSizeX;
    }

    /**
     * @return
     */
    public int getRoomSizeY() {
        return roomSizeY;
    }
    
    /**
     * @return
     */
    public int getLevelizeX() {
        return levelgrid.length;
    }
    
    /**
     * @return
     */
    public int getLevelizeY() {
        return levelgrid[0].length;
    }
    
    

}
