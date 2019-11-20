package level;

/// <summary>
/// Contains level data (all rooms and some meta informations, like lvl number)
/// </summary>
public class LevelData {
	
	 private RoomData[] levelRoomArray;
     private int lvlNumber;

     public LevelData(RoomData[] lvlrmarr, int lvlnum)
     {
         levelRoomArray = lvlrmarr;
         lvlNumber = lvlnum;
     }

     public RoomData[] getLevelRoomArray()
     {
         return levelRoomArray;
     }

     public int getLevelNumber()
     {
         return lvlNumber;
     }

}
