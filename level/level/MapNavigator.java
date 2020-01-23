package level;

import java.util.HashMap;
import java.util.UUID;

import javafx.geometry.Point2D;
import settings.Settings;

/**
 * Class for application wide tracking of Player-Position within map ( use at
 * your own risk :-) )
 * 
 * @author Roman
 *
 */
public class MapNavigator {

	private HashMap<Direction, Point2D> entryCoords;

	private LevelData currentLevel;
	private RoomData currentRoom;

	private static MapNavigator instance;

	private MapNavigator() {
		
		entryCoords = new HashMap<Direction, Point2D>();
		
		// Entry points for level change
		entryCoords.put(Direction.NORTH, 
				new Point2D(Settings.getWindowWidth() / 2, Settings.getBlocksize() * 2));		
		entryCoords.put(Direction.SOUTH,
				new Point2D(Settings.getWindowWidth() / 2, Settings.getWindowHeight() - Settings.getBlocksize() * 2));
		entryCoords.put(Direction.WEST, 
				new Point2D(Settings.getBlocksize() * 2, Settings.getWindowHeight() / 2));
		entryCoords.put(Direction.EAST,
				new Point2D(Settings.getWindowHeight() - Settings.getBlocksize() * 21, Settings.getWindowHeight() / 2));
	}

	public static MapNavigator getInstance() {
		if (MapNavigator.instance == null) {
			MapNavigator.instance = new MapNavigator();
		}
		return MapNavigator.instance;
	}

	/**
	 * @param lvl
	 */
	public void changeLevel(LevelData lvl) {
		currentLevel = lvl;
	}
	
	/**
	 * @param lvl
	 */
	public void changecCurrentRoom(UUID room) {
		currentRoom = currentLevel.getLevelRoomList().get(room);
	}	
	
	public LevelData getLevel() {
		
		return currentLevel;		
	}	

	/**
	 * player redraw on room change
	 * 
	 * @param dir
	 * @return
	 */
	public Point2D getEntryCoords(Direction dir) {
		return entryCoords.get(dir);
	}

}
