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

     public LevelData(HashMap<UUID, RoomData> lvlrm,UUID[][] roomgrd ,int[][] lvlgrd, int lvlnum)
     {
    	 roomList = lvlrm;
         roomGrid = roomgrd;
         levelgrid = lvlgrd;
         lvlNumber = lvlnum;
     }

     public HashMap<UUID, RoomData> getLevelRoomList()
     {
         return roomList;
     }
     
     public	 UUID[][] getLevelRoomGrid()
     {
    	 return roomGrid;
     }
     
     public	 int[][] getLevelGrid()
     {
    	 return levelgrid;
     }

     public int getLevelNumber()
     {
         return lvlNumber;
     }

}
