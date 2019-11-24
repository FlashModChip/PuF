package level;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class LevelGenerator
{
    //Room generation Values
    private int roomNumber;

    private int roomTileSizeX;
    private int roomTileSizeY;

    //Overall enemies in Level
    private int enemyNumber;

    //Max enemies per room
    private int enemyThreshold;

    //Overall items per level
    private int itemNumber;

    //Max items per level
    private int itemThreshold;
    
    private String[] dir = { "north", "east", "south", "west" };

    /// <summary>
    /// Default constructor
    /// </summary>
    public LevelGenerator()
    {
        roomNumber = 5;
        roomTileSizeX = 20;
        roomTileSizeY = 10;
        enemyNumber = 5;
        enemyThreshold = 2;
        itemNumber = 5;
        itemThreshold = 1;
    }
   
    public LevelGenerator(int roomnum, int roomtilex, int roomtiley, int enemynum, int enemythresh, int itemnum, int itemtresh)
    {
        roomNumber = roomnum;
        roomTileSizeX = roomtilex;
        roomTileSizeY = roomtiley;
        enemyNumber = enemynum;
        enemyThreshold = enemythresh;
        itemNumber = itemnum;
        itemThreshold = itemtresh;
    }
  
    public void setLayout(int roomnum, int roomtilex, int roomtiley)
    {
        roomNumber = roomnum;
        roomTileSizeX = roomtilex;
        roomTileSizeY = roomtiley;
    }

    public void seEnemyLayout(int enemynum, int enemythresh)
    {
        enemyNumber = enemynum;
        enemyThreshold = enemythresh;
    }

   
    public void seItemLayout(int itemnum, int itemtresh)
    {
        enemyNumber = itemnum;
        enemyThreshold = itemtresh;
    }
   
    public LevelData generateLevel(int lvlnum)
    {
    	Random ran = new Random();

        //initialize room list and grid
    	HashMap<UUID, RoomData> roomList = new HashMap<>();
        UUID[][] roomGrid = new UUID[roomNumber * 2][ roomNumber * 2];

        //set start point of room generation crawler
        RoomData roomPointer = new RoomData();
        roomList.put(roomPointer.getRoomID(),roomPointer);
        Point roomCoord = new Point(roomNumber, roomNumber);

        //mark start in the center of the grid
        roomGrid[roomCoord.x][ roomCoord.y] = roomPointer.getRoomID();

        //crawling until there is no room count left
        for (int i = 0; i < roomNumber - 1; i++)
        {
            RoomData tmp = null;
            boolean newRoom = false;

            //random direction
            switch (dir[ran.nextInt(3)])
            {
                case "north":
                    //if room has an adjacent (north) room and there is no other room create new room and connect them
                    if ((new UUID(0,0) == roomPointer.getadjacentRoom("north")) && (roomGrid[roomCoord.x][ roomCoord.x - 1] == new UUID(0,0) ))
                    {
                        tmp = new RoomData();
                        connectRooms(roomPointer, tmp, "north");
                        roomGrid[roomCoord.x][ roomCoord.y - 1] = tmp.getRoomID();
                        //move pointers
                        roomCoord.y--;
                        roomPointer = tmp;
                        newRoom = true;
                    }
                    //if there is already a room, but not connected, connect them
                    else if (roomGrid[roomCoord.x][ roomCoord.y - 1] != new UUID(0,0))
                    {
                        tmp = roomList.get(roomGrid[roomCoord.x][roomCoord.y - 1]);
                        connectRooms(roomPointer, tmp, "north");
                        roomCoord.y--;
                        roomPointer = tmp;
                    }
                    break;

                case "south":
                	if ((new UUID(0,0) == roomPointer.getadjacentRoom("north")) && (roomGrid[roomCoord.x][ roomCoord.x - 1] == new UUID(0,0) ))
                    {
                        tmp = new RoomData();
                        connectRooms(roomPointer, tmp, "south");
                        roomGrid[roomCoord.x][roomCoord.y + 1] = tmp.getRoomID();
                        roomCoord.y++;
                        roomPointer = tmp;
                        newRoom = true;
                    }
                    else if (roomGrid[roomCoord.x][roomCoord.y + 1] != new UUID(0,0))
                    {
                    	tmp = roomList.get(roomGrid[roomCoord.x][roomCoord.y + 1]);
                        connectRooms(roomPointer, tmp, "south");
                        roomCoord.y++;
                        roomPointer = tmp;
                    }
                    break;

                case "west":
                    if ((new UUID(0,0) == roomPointer.getadjacentRoom("west")) && (roomGrid[roomCoord.x - 1][ roomCoord.y] == new UUID(0,0)))
                    {
                        tmp = new RoomData();
                        connectRooms(roomPointer, tmp, "west");
                        roomGrid[roomCoord.x - 1][roomCoord.y] = tmp.getRoomID();
                        roomCoord.x--;
                        roomPointer = tmp;
                        newRoom = true;
                    }
                    else if (roomGrid[roomCoord.x - 1][ roomCoord.y] != new UUID(0,0))
                    {
                    	tmp = roomList.get(roomGrid[roomCoord.x-1][roomCoord.y]);
                        connectRooms(roomPointer, tmp, "west");
                        roomCoord.x--;
                        roomPointer = tmp;
                    }
                    break;

                case "east":
                    if ((new UUID(0,0) == roomPointer.getadjacentRoom("east")) && (roomGrid[roomCoord.x + 1][ roomCoord.y] == new UUID(0,0)))
                    {
                        tmp = new RoomData();
                        connectRooms(roomPointer, tmp, "east");
                        roomGrid[roomCoord.x + 1][ roomCoord.y] = tmp.getRoomID();
                        roomCoord.x++;
                        roomPointer = tmp;
                        newRoom = true;
                    }
                    else if (roomGrid[roomCoord.x + 1][ roomCoord.y] != new UUID(0,0))
                    {
                    	tmp = roomList.get(roomGrid[roomCoord.x+1][roomCoord.y]);
                        connectRooms(roomPointer, tmp, "east");
                        roomCoord.x++;
                        roomPointer = tmp;
                    }
                    break;
            }

            //If there was no additional room, try again, until room counter is reached
            if (newRoom)
            {
                roomList.put(tmp.getRoomID(),tmp);
            }
            else
            {
                i--;
            }
        }

        generateRooms(roomList);

        System.out.println(Arrays.deepToString(roomGrid));
        
        return new LevelData(roomList,roomGrid,lvlnum);
    }

    public byte[][] generateLevelbyTemplates(String dir)
    {       
        //
        //ToDo
        //

        return new byte[1][1];
    }

    
    /**
     * Creates a randomly generated room for each entry in a given list of rooms
     * @param roomList
     * @return
     */
    private HashMap<UUID,RoomData> generateRooms(HashMap<UUID,RoomData> roomList)
    {
        //overall enemies and item per level
        int enemies = this.enemyNumber;
        int items = this.itemNumber;
        
        for(Map.Entry<UUID, RoomData> entry : roomList.entrySet()) 
        {
        	 GenerateSingleRoom(entry.getValue());
        	 
        }  
        
        return null;
    }

    private RoomData GenerateSingleRoom(RoomData room)
    {
        //tile codes (should  probably be an enum XD)
        byte wall =   (byte) 255;
        //corner tiles?
        byte ground = (byte) 100;
        byte door =   (byte) 150;
        byte enemy =  (byte) 20;
        //enemies kind?
        byte item =   (byte) 55;
        //item kind??

        byte[][] roomdata = new byte[roomTileSizeX][roomTileSizeY];

        Random ran = new Random();

        int enemycount = ran.nextInt((this.enemyThreshold));
        
        int itemcount = ran.nextInt((this.itemThreshold));              

        for (int i = 0; i < roomdata.length; i++)
        {
            for (int j = 0; j < roomdata[0].length; j++)
            {
                //walls
                if (isWall(i, j, roomdata))
                {
                    //doors halfway in walls
                    if (isDoorPosition(i, j, roomdata))
                    {
                        //check adjacent rooms
                        if ((room.getadjacentRoom("west") != new UUID(0,0)) && (i == 0)) { roomdata[i][j] = door; }
                        else if ((room.getadjacentRoom("east") != new UUID(0,0)) && (i == roomdata.length - 1)) { roomdata[i][j] = door; }
                        else if ((room.getadjacentRoom("north") != new UUID(0,0)) && (j == 0)) { roomdata[i][j] = door; }
                        else if ((room.getadjacentRoom("south") != new UUID(0,0)) && (j == roomdata[0].length - 1)) { roomdata[i][j] = door; }
                        else { roomdata[i][j] = wall; }
                    }
                    else
                    {
                    	roomdata[i][j] = wall;
                    }
                }
                // ground
                else
                {
                    // highly complex enemy random distribution algorithm
                    if ((ran.nextInt(roomdata.length + enemycount) == 0) && (enemycount > 0))
                    {
                    	roomdata[i][j] = enemy;
                        enemycount--;
                        //enemies = enemies - 1;
                    }
                    // advanced random item algorithm
                    else if ((ran.nextInt(roomdata.length + enemycount)== 0) && (itemcount > 0))
                    {
                    	roomdata[i][j] = item;
                        itemcount--;
                        //items--;
                    }
                    else
                    {
                    	roomdata[i][j] = ground;
                    }
                }
            }
        }
        room.setRoomData(roomdata);
        return room;
    }
    
    
    private boolean isWall(int i, int j, byte[][] arr)
    {
        return (i == 0) || (j == 0) || (i == arr.length - 1) || (j == arr[0].length - 1);
    }

   
    private boolean isDoorPosition(int i, int j, byte[][] arr)
    {
        return (i == arr.length / 2 || j == arr[0].length / 2);
    }
    
    
  
    private boolean connectRooms(RoomData room, RoomData room2, String direction)
    {
        String counterdirection = "";

        if (!room.alreadyadjacent(room2.getRoomID()))
        {
            switch (direction)
            {
                case "north":
                    counterdirection = "south";
                    break;

                case "south":
                    counterdirection = "north";
                    break;

                case "west":
                    counterdirection = "east";
                    break;

                case "east":
                    counterdirection = "west";
                    break;

                default:
                    break;
            }

            room.setadjacentRoom(room2, direction);
            room2.setadjacentRoom(room, counterdirection);

            return true;
        }
        else
        {
            return false;
        }
    }
}
