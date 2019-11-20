package level;

import java.util.Random;

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

    /// <summary>
    /// Parameterized construcor
    /// </summary>
    /// <param name="roomnum">
    /// </param>
    /// <param name="roomtilex">
    /// </param>
    /// <param name="roomtiley">
    /// </param>
    /// <param name="enemynum">
    /// </param>
    /// <param name="enemythresh">
    /// </param>
    /// <param name="itemnum">
    /// </param>
    /// <param name="itemtresh">
    /// </param>
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

    /// <summary>
    /// Changing enemy layout
    /// </summary>
    /// <param name="roomnum">
    /// </param>
    /// <param name="roomtilex">
    /// </param>
    /// <param name="roomtiley">
    /// </param>
    public void setLayout(int roomnum, int roomtilex, int roomtiley)
    {
        roomNumber = roomnum;
        roomTileSizeX = roomtilex;
        roomTileSizeY = roomtiley;
    }

    /// <summary>
    /// enemy layout
    /// </summary>
    /// <param name="enemynum">
    /// </param>
    /// <param name="enemythresh">
    /// </param>
    public void seEnemyLayout(int enemynum, int enemythresh)
    {
        enemyNumber = enemynum;
        enemyThreshold = enemythresh;
    }

    /// <summary>
    /// item layout
    /// </summary>
    /// <param name="itemnum">
    /// </param>
    /// <param name="itemtresh">
    /// </param>
    public void seItemLayout(int itemnum, int itemtresh)
    {
        enemyNumber = itemnum;
        enemyThreshold = itemtresh;
    }

    /// <summary>
    /// Generates a level using specified parameters
    /// </summary>
    /// <returns>
    /// </returns>
    public byte[][] generateLevel()
    {
        for (int i = 0; i <= roomNumber; i++)
        {
            //
            //ToDo (matching entrances)
            //
        }

        return new byte[1][1];
    }

    public byte[][] generateLevelbyTemplates(String dir)
    {
        String[] directions = { "north", "east", "south", "west" };

        Random ran = new Random();

        //
        //ToDo
        //

        return new byte[1][1];
    }

    private RoomData generateRoom(int roomTileSizeX, int roomTileSizeY, bool north, bool east, bool south, bool west)
    {
        //
        //ToDo (room-Tile algorithm)
        //

        return new RoomData(null, north, east, south, west);
    }
}
