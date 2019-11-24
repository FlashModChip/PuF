package level;

import java.util.UUID;


/**
 * @author Roman
 *Room node class for level (tile data layout and 4 connections to adjacent rooms)
 */
public class RoomData
{
	private byte[][] roomData;

    private UUID adjacentRoomNorth;
    private UUID adjacentRoomSouth;
    private UUID adjacentRoomWest;
    private UUID adjacentRoomEast;

    private UUID roomID;

    public RoomData()
    {
        roomData = null;

        adjacentRoomNorth = new UUID(0,0);
        adjacentRoomSouth = new UUID(0,0);
        adjacentRoomWest = new UUID(0,0);
        adjacentRoomEast = new UUID(0,0);

        roomID = UUID.randomUUID();
    }

    public RoomData(byte[][] data, UUID north, UUID east, UUID south, UUID west)
    {
        roomData = data;

        adjacentRoomNorth = north;
        adjacentRoomSouth = south;
        adjacentRoomWest = west;
        adjacentRoomEast = east;

        roomID = UUID.randomUUID();
    }

    public UUID getRoomID()
    {
        return roomID;
    }
   
    public int getDimensionX()
    {
        return roomData.length;
    }

    public int getDimensionY()
    {
        return roomData[0].length;
    }

    public byte[][] getRoomData()
    {
        return roomData;
    }

    public void setRoomData(byte[][] val)
    {
        this.roomData = val;
    }
    
    public void setadjacentRoom(RoomData room, String direction)
    {
        switch (direction)
        {
            case "north":
                adjacentRoomNorth = room.getRoomID();
                break;

            case "south":
                adjacentRoomSouth = room.getRoomID();
                break;

            case "west":
                adjacentRoomWest = room.getRoomID();
                break;

            case "east":
                adjacentRoomEast = room.getRoomID();
                break;

            default:
                break;
        }
    }
   
    public UUID getadjacentRoom(String direction)
    {
        switch (direction)
        {
            case "north":
                return adjacentRoomNorth;                

            case "south":
                return adjacentRoomSouth;              

            case "west":
                return adjacentRoomWest;              

            case "east":
                return adjacentRoomEast;               
        }

        return UUID.fromString(null);
    }
 
    public boolean alreadyadjacent(UUID roomID)
    {
        return this.adjacentRoomNorth == roomID ||
               this.adjacentRoomEast == roomID ||
               this.adjacentRoomSouth == roomID ||
               this.adjacentRoomWest == roomID;
    }
}

