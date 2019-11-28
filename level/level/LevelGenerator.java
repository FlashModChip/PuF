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
    
    private String path = "./level/roomTemplates/";
    

    /// <summary>
    /// Default constructor
    /// </summary>
    public LevelGenerator()
    {
        roomNumber = 5;
        roomTileSizeX = 20;
        roomTileSizeY = 10;
        enemyNumber = 5;
        enemyThreshold = 5;
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
   
    public LevelData generateLevel(int lvlnum, boolean fromFile)
    {
    	Random ran = new Random();

        //initialize room list and grid (to map the crawlers path)
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
            switch (Direction.values()[ran.nextInt(4)])
            {
                case NORTH:
                    //if room has an adjacent (north) room and there is no other room create new room and connect them
                    if ((new UUID(0,0).equals(roomPointer.getadjacentRoom(Direction.NORTH))) && (roomGrid[roomCoord.x- 1][ roomCoord.x ] == null ))
                    {
                        tmp = new RoomData();
                        connectRooms(roomPointer, tmp, Direction.NORTH);
                        roomGrid[roomCoord.x- 1][ roomCoord.y] = tmp.getRoomID();
                        //move pointers
                        roomCoord.x--;
                        roomPointer = tmp;
                        newRoom = true;
                    }
                    //if there is already a room, but not connected, connect them
                    else if (roomGrid[roomCoord.x- 1][ roomCoord.y ] != null)
                    {
                        tmp = roomList.get(roomGrid[roomCoord.x - 1][roomCoord.y]);
                        connectRooms(roomPointer, tmp, Direction.NORTH);
                        roomCoord.x--;
                        roomPointer = tmp;
                    }
                    break;

                case SOUTH:
                	if ((new UUID(0,0).equals(roomPointer.getadjacentRoom(Direction.NORTH))) && (roomGrid[roomCoord.x + 1][ roomCoord.y] == null ))
                    {
                        tmp = new RoomData();
                        connectRooms(roomPointer, tmp, Direction.SOUTH);
                        roomGrid[roomCoord.x + 1][roomCoord.y] = tmp.getRoomID();
                        roomCoord.x++;
                        roomPointer = tmp;
                        newRoom = true;
                    }
                    else if (roomGrid[roomCoord.x + 1][roomCoord.y ] != null)
                    {
                    	tmp = roomList.get(roomGrid[roomCoord.x + 1][roomCoord.y]);
                        connectRooms(roomPointer, tmp, Direction.SOUTH);
                        roomCoord.x++;
                        roomPointer = tmp;
                    }
                    break;

                case WEST:
                    if ((new UUID(0,0).equals(roomPointer.getadjacentRoom(Direction.WEST))) && (roomGrid[roomCoord.x][ roomCoord.y- 1] == null))
                    {
                        tmp = new RoomData();
                        connectRooms(roomPointer, tmp, Direction.WEST);
                        roomGrid[roomCoord.x ][roomCoord.y- 1] = tmp.getRoomID();
                        roomCoord.y--;
                        roomPointer = tmp;
                        newRoom = true;
                    }
                    else if (roomGrid[roomCoord.x ][ roomCoord.y - 1 ] != null)
                    {
                    	tmp = roomList.get(roomGrid[roomCoord.x][roomCoord.y-1]);
                        connectRooms(roomPointer, tmp, Direction.WEST);
                        roomCoord.y--;
                        roomPointer = tmp;
                    }
                    break;

                case EAST:
                    if ((new UUID(0,0).equals(roomPointer.getadjacentRoom(Direction.EAST))) && (roomGrid[roomCoord.x][ roomCoord.y + 1] == null))
                    {
                        tmp = new RoomData();
                        connectRooms(roomPointer, tmp, Direction.EAST);
                        roomGrid[roomCoord.x][ roomCoord.y + 1] = tmp.getRoomID();
                        roomCoord.y++;
                        roomPointer = tmp;
                        newRoom = true;
                    }
                    else if (roomGrid[roomCoord.x ][ roomCoord.y+ 1] != null)
                    {
                    	tmp = roomList.get(roomGrid[roomCoord.x][roomCoord.y+1]);
                        connectRooms(roomPointer, tmp, Direction.EAST);
                        roomCoord.y++;
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

        if(fromFile)
        {
          generateRoomsFromTemplate(roomList);
        }
        {
          //generateRooms(roomList);
          generateRoomsFromTemplate(roomList);
        }   

        //cut off empty columns /rows
        roomGrid = resizeArray(roomGrid);        
        
        //generate lvl grid (maybe easier for drawing level?
        int[][] tmp= roomGridToArray(roomGrid,roomList);
               
        //debug
        print2D(roomGrid, roomList, tmp); 
        //System.out.print(getRoomDataSingleTile(18,8,roomGrid,roomList));
        
        //LevelPicUtil.getLevelDatafromPNG(path, 20, 10);
        
        return new LevelData(roomList,roomGrid,tmp ,lvlnum);
    }

    
    /**
     * Creates a randomly generated room for each entry in a given list of rooms
     * @param roomList
     * @return
     */
    private HashMap<UUID,RoomData> generateRooms(HashMap<UUID,RoomData> roomList)
    {        
        
        for(Map.Entry<UUID, RoomData> entry : roomList.entrySet()) 
        {
        	 GenerateSingleRoom(entry.getValue());        	 
        }  
        
        return null;
    }    
    
    
    /**
     * Creates a room by template for each entry in a given list of rooms
     * @param roomList
     * @return
     */
    private HashMap<UUID,RoomData> generateRoomsFromTemplate(HashMap<UUID,RoomData> roomList)
    {        
        
        for(Map.Entry<UUID, RoomData> entry : roomList.entrySet()) 
        {
        	GenerateSingleRoomByTemplate(entry.getValue());        	 
        }  
        
        return null;
    }
    
    

    /**
     * Generates a single room
     * @param room
     * @return
     */
    private RoomData GenerateSingleRoom(RoomData room)
    {
    	int[][] roomdata = new int[roomTileSizeX][roomTileSizeY];

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
                        if ((!room.getadjacentRoom(Direction.WEST).equals(new UUID(0,0))) && (i == 0)) { roomdata[i][j] = TileCode.door; }
                        else if ((!room.getadjacentRoom(Direction.EAST).equals(new UUID(0,0))) && (i == roomdata.length - 1)) { roomdata[i][j] =TileCode. door; }
                        else if ((!room.getadjacentRoom(Direction.NORTH).equals(new UUID(0,0))) && (j == 0)) { roomdata[i][j] = TileCode.door; }
                        else if ((!room.getadjacentRoom(Direction.SOUTH).equals(new UUID(0,0)))&& (j == roomdata[0].length - 1)) { roomdata[i][j] = TileCode.door; }
                        else { roomdata[i][j] = TileCode.wall; }
                    }
                    else
                    {
                    	roomdata[i][j] = TileCode.wall;
                    }
                }
                // ground
                else
                {
                    // highly complex enemy random distribution algorithm
                    if ((ran.nextInt(roomdata.length + enemycount) == 0) && (enemycount > 0))
                    {
                    	roomdata[i][j] = TileCode.enemy;
                        enemycount--;                        
                    }
                    // advanced random item algorithm
                    else if ((ran.nextInt(roomdata.length + enemycount)== 0) && (itemcount > 0))
                    {
                    	roomdata[i][j] = TileCode.item;
                        itemcount--;                      
                    }
                    else
                    {
                    	roomdata[i][j] = TileCode.ground;
                    }
                }
            }
        }
        room.setRoomData(roomdata);
        return room;
    }
    
    
    /**
     * Generates a single room by template using LevelPicUtil
     * @param room
     * @return
     */
    private RoomData GenerateSingleRoomByTemplate(RoomData room)
    {
    	int[][] roomdata = LevelPicUtil.getLevelDatafromPNG(path, roomTileSizeX, roomTileSizeY);
    	                       
    	//assigning door (templates dont know where the doors are!)
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
                        if ((!room.getadjacentRoom(Direction.WEST).equals(new UUID(0,0))) && (i == 0)) { roomdata[i][j] = TileCode.door; }
                        else if ((!room.getadjacentRoom(Direction.EAST).equals(new UUID(0,0))) && (i == roomdata.length - 1)) { roomdata[i][j] =TileCode. door; }
                        else if ((!room.getadjacentRoom(Direction.NORTH).equals(new UUID(0,0))) && (j == 0)) { roomdata[i][j] = TileCode.door; }
                        else if ((!room.getadjacentRoom(Direction.SOUTH).equals(new UUID(0,0)))&& (j == roomdata[0].length - 1)) { roomdata[i][j] = TileCode.door; }
                        else { roomdata[i][j] = TileCode.wall; }
                    }                   
                }              
            }
        }
    	
        room.setRoomData(roomdata);
        return room;
    }
    
    
    private boolean isWall(int i, int j, int[][] arr)
    {
        return (i == 0) || (j == 0) || (i == arr.length - 1) || (j == arr[0].length - 1);
    }

   
    private boolean isDoorPosition(int i, int j, int[][] arr)
    {
        return (i == arr.length / 2 || j == arr[0].length / 2);
    }
    
    
    
    /**
     * Connects 2 rooms in a specific direction
     * @param room
     * @param room2
     * @param direction
     * @return
     */
    private boolean connectRooms(RoomData room, RoomData room2, Direction direction)
    {
        Direction counterdirection = null;

        if (!room.alreadyadjacent(room2.getRoomID()))
        {
            switch (direction)
            {
                case NORTH:
                    counterdirection = Direction.SOUTH;
                    break;

                case SOUTH:
                    counterdirection = Direction.NORTH;
                    break;

                case WEST:
                    counterdirection = Direction.EAST;
                    break;

                case EAST:
                    counterdirection = Direction.WEST;
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
    
  //Resizes a 2d array (deletes empty rows and columns)
    private static UUID[][] resizeArray(UUID[][] source) {

        //checking bounds
        int xStart, xEnd, yStart, yEnd;
        xStart = yStart = source.length;
        xEnd = yEnd = 0;

        for  (int i = 0;i<source.length;i++ )
        {
            for  (int j = 0;j < source[0].length; j++ )
            {
                if(source[i][j] != null)
                {
                    if(xStart > i) {xStart =i;}               
                    if(yStart > j) {yStart =j;}
                    if(xEnd < i) {xEnd =i;}
                    if(yEnd < j) {yEnd =j;}
                }
            }
        }
        //create new smaller array
        int height = xEnd-xStart;
        int width = yEnd-yStart;

        UUID[][] dest = new UUID[height+1][width+1];

        for (int i= 0; i <= height ; i++) 
        {
            for(int j = 0; j <= width; j++)
            {
                dest[i][j] = source[i+xStart][j+yStart];                
            }
        }

        return dest;
    }
   
    
    /**
     * converts a roomlist and a roomgrid in an 2d array
     * @param grid
     * @param roomlist
     * @return
     */
    private int[][] roomGridToArray(UUID[][] grid, HashMap<UUID, RoomData> roomlist) {

        //int tilesizeX = 20;
        //int tilesizeY = 10;

        //create new array(individual roomsizes*rooms)
        int[][]result = new int[grid[0].length*roomTileSizeX][grid.length*roomTileSizeY];
        
                
        for  (int i = 0;i<grid.length;i++ )
        {
            for  (int j = 0;j < grid[0].length; j++ )
            {
                //Fill data of a single room in giant grid
                if(grid[i][j] != null && roomlist.containsKey(grid[i][j]))
                {
                RoomData tempRoom = roomlist.get(grid[i][j]);

                    for  (int k = 0;k<tempRoom.getRoomData().length;k++ )
                    {
                        for  (int l = 0;l < tempRoom.getRoomData()[0].length; l++ )
                        {
                            result[(j*roomTileSizeX)+k][(i*roomTileSizeY)+l] = tempRoom.getRoomData()[k][l];                            
                        }
                    }
                }
            }
        }       

        return result;
    }

  
    /**
     * mapping coordinates of roomGridToArray - array to roomlist + grid containers
     * @param x
     * @param y
     * @param grid
     * @param roomlist
     * @return
     */
    private int getRoomDataSingleTile(int x,int y, UUID[][] grid, HashMap<UUID,RoomData> roomlist) {

        int roomCoordX = (int)x/roomTileSizeX;
        int roomCoordY = (int)y/roomTileSizeY;

        return roomlist.get(grid[roomCoordX][roomCoordY]).getRoomData()[x-(roomCoordX*roomTileSizeX)][y-(roomCoordY*roomTileSizeY)];
        
    }
   
    private void setRoomDataSingleTile(int x,int y,int val, UUID[][] grid, HashMap<UUID,RoomData> roomlist) {

        int roomCoordX = (int)x/roomTileSizeX;
        int roomCoordY = (int)y/roomTileSizeY;
        
        roomlist.get(grid[roomCoordX][roomCoordY]).getRoomData()[x-(roomCoordX*roomTileSizeX)][y-(roomCoordY*roomTileSizeY)] = val;
        
    }
    
   
    /**
     * Prints debug info to console
     * @param roomGrid
     * @param roomlist
     */
    public static void print2D(UUID[][] roomGrid, HashMap<UUID, RoomData> roomlist, int[][] largearr) 
    { 
        // Loop through all rows 
        for (int i = 0; i < roomGrid.length; i++) 
        {  
            // Loop through all elements of current row 
            for (int j = 0; j < roomGrid[i].length; j++) 
            {
            	if(roomGrid[i][j] == null) 
            	{
            		System.out.print((new UUID(0,0)).toString().substring(0,4) + " ");
            	}
            	else
            	{
                System.out.print(roomGrid[i][j].toString().substring(0,4) + " "); 
            	}
            }
            System.out.println("");
        }
        
        System.out.println("");
        
        for(Map.Entry<UUID, RoomData> entry : roomlist.entrySet()) 
        {
        	 System.out.println("ID:" + entry.getKey().toString().substring(0,4)+" "+
        			 "North:" +entry.getValue().getadjacentRoom(Direction.NORTH).toString().substring(0,4)+" "+
        			 "South:" +entry.getValue().getadjacentRoom(Direction.SOUTH).toString().substring(0,4)+" "+
        			 "West:" +entry.getValue().getadjacentRoom(Direction.WEST).toString().substring(0,4)+" "+
        			 "East:" +entry.getValue().getadjacentRoom(Direction.EAST).toString().substring(0,4));         	 
        }  
        
        System.out.println("");  
        
        for(Map.Entry<UUID, RoomData> entry : roomlist.entrySet()) 
        {
        	for (int i = 0; i < entry.getValue().getRoomData()[i].length; i++) 
            {                 
                for (int j = 0; j < entry.getValue().getRoomData().length; j++) 
                {                	
                    System.out.print(entry.getValue().getRoomData()[j][i] + " ");                	
                }
                System.out.println("");
            }
        	System.out.println("");    
        } 
        
        // Loop through all rows 
        for (int i = 0; i < largearr[0].length; i++) 
        {  
            // Loop through all elements of current row 
            for (int j = 0; j < largearr.length; j++) 
            {
            	System.out.print((largearr[j][i] != 0 ? largearr[j][i]:"000" )+" "); 
            
            }
            System.out.println("");
        }
        
    } 
}
