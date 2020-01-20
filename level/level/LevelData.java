package level;

import java.util.HashMap;
import java.util.UUID;

/// <summary>
/// Contains level data (all rooms and some meta informations, like lvl number)
/// </summary>
public class LevelData {
	
	//List of individual room data
	 private HashMap<UUID, RoomData> roomList;
	 
	 //2 dimensional array of rooms (via ID reference)
	 private UUID[][] roomGrid;
	 
     //2 dimensional array of level data  
	 private int[][] levelgrid;
     
     private int lvlNumber;

     /**
     * @param lvlrm
     * @param roomgrd
     * @param lvlgrd
     * @param lvlnum
     */
    public LevelData(HashMap<UUID, RoomData> lvlrm,UUID[][] roomgrd ,int[][] lvlgrd, int lvlnum)
     {
    	 roomList = lvlrm;
         roomGrid = roomgrd;
         levelgrid = lvlgrd;
         lvlNumber = lvlnum;
     }

     /**
     * @return
     */
    public HashMap<UUID, RoomData> getLevelRoomList()
     {
         return roomList;
     }
     
     /**
     * @return
     */
    public	 UUID[][] getLevelRoomGrid()
     {
    	 return roomGrid;
     }
     
     /**
     * @return
     */
    public	 int[][] getLevelGrid()
     {
    	 return levelgrid;
     }

     /**
     * @return
     */
    public int getLevelNumber()
     {
         return lvlNumber;
     }

}
