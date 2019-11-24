package level;

import java.util.HashMap;
import java.util.UUID;

/// <summary>
/// Contains level data (all rooms and some meta informations, like lvl number)
/// </summary>
public class LevelData {
	
	 HashMap<UUID, RoomData> roomList;
     UUID[][] roomGrid;
     private int lvlNumber;

     public LevelData(HashMap<UUID, RoomData> lvlrm,UUID[][] roomgrd , int lvlnum)
     {
    	 roomList = lvlrm;
         roomGrid = roomgrd;
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

     public int getLevelNumber()
     {
         return lvlNumber;
     }

}
