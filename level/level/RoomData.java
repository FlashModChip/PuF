package level;

import java.util.UUID;

public class RoomData
{
    private byte[][] roomData;

    private Boolean northdoor;
    private Boolean southdoor;
    private Boolean westdoor;
    private Boolean easthdoor;

    private UUID adjecentRoomNorth;
    private UUID adjecentRoomSouth;
    private UUID adjecentRoomWest;
    private UUID adjecentRoomEast;

    private UUID roomID;

    public RoomData(byte[][] data, Boolean north, Boolean east, Boolean south, Boolean west)
    {
        roomData = data;

        northdoor = north;
        southdoor = south;
        westdoor = west;
        easthdoor = east;

        roomID = UUID.randomUUID();
    }

    public UUID getRoomID()
    {
        return roomID;
    }

    /// <summary>
    /// Room X dimension
    /// </summary>
    /// <returns>
    /// </returns>
    public int getDimensionX()
    {
        return roomData.length;
    }

    /// <summary>
    /// Room Y dimension
    /// </summary>
    /// <returns>
    /// </returns>
    public int getDimensionY()
    {
        return roomData[0].length;
    }

    /// <summary>
    /// Room data array
    /// </summary>
    /// <returns>
    /// </returns>
    public byte[][] getRoomData()
    {
        return roomData;
    }

    /// <summary>
    /// .
    /// </summary>
    /// <returns>
    /// </returns>
    public Boolean hasNorthDoor()
    {
        return northdoor;
    }

    /// <summary>
    /// .
    /// </summary>
    /// <returns>
    /// </returns>
    public Boolean hasSouthDoor()
    {
        return southdoor;
    }

    /// <summary>
    /// .
    /// </summary>
    /// <returns>
    /// </returns>
    public Boolean hasWestDoor()
    {
        return westdoor;
    }

    /// <summary>
    /// .
    /// </summary>
    /// <returns>
    /// </returns>
    public Boolean hasEastDoor()
    {
        return easthdoor;
    }

    /// <summary>
    /// set ajecent room (checks if input room has fitting door)
    /// </summary>
    /// <param name="room">
    /// </param>
    /// <param name="direction">
    /// north,south,east or west
    /// </param>
    public void setAdjecentRoom(RoomData room, String direction)
    {
        switch (direction)
        {
            case "north":
                if (room.hasNorthDoor())
                {
                    adjecentRoomNorth = room.getRoomID();
                }

                break;

            case "south":
                if (room.hasSouthDoor())
                {
                    adjecentRoomSouth = room.getRoomID();
                }

                break;

            case "west":
                if (room.hasEastDoor())
                {
                    adjecentRoomWest = room.getRoomID();
                }

                break;

            case "east":
                if (room.hasWestDoor())
                {
                    adjecentRoomEast = room.getRoomID();
                }

                break;

            default:
                break;
        }
    }

    /// <summary>
    /// return UUID of specific adjecent room
    /// </summary>
    /// <param name="direction">
    /// </param>
    /// <returns>
    /// </returns>
    public UUID getAdjecentRoom(String direction)
    {
        switch (direction)
        {
            case "north":
                if (hasNorthDoor())
                {
                    return adjecentRoomNorth;
                }
                break;

            case "south":
                if (hasSouthDoor())
                {
                    return adjecentRoomSouth;
                }
                break;

            case "west":
                if (hasWestDoor())
                {
                    return adjecentRoomWest;
                }
                break;

            case "east":
                if (hasEastDoor())
                {
                    return adjecentRoomEast;
                }
                break;
        }

        return UUID.fromString("");
    }
}
