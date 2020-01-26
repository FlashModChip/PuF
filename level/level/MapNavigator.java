package level;

import java.util.HashMap;
import java.util.UUID;

import javafx.geometry.Point2D;
import settings.Settings;

/**
 * Class for application wide tracking of Player-Position within map ( use at
 * your own risk :-) ) *
 * 
 * ----------------------------------------------------------------------------
 * 
 * How To:
 * 
 * MapNavigator wird mit einem neuen Level des LevelGenerator instantiiert
 * 
 * LevelGenerator gen = new LevelGenerator(); MapNavigator nav =
 * MapNavigator.getInstance(); * nav.changeLevel(gen.generateLevel(1, false));
 * 
 * ----------------------------------------------------------------------------
 * 
 * Mit einer Methode des MapNavigator ermitteln wir den Startraum
 * 
 * nav.setStartingRoom();
 * 
 * ----------------------------------------------------------------------------
 * 
 * Nun haben wir Zugriff auf die Daten des aktuellen Raumes und können diesen
 * zeichnen
 * 
 * nav.getCurrentRoom().getRoomData()
 * 
 * ----------------------------------------------------------------------------
 * 
 * Sollte ein Exit-Ereignis ausgelöst werden, muss man die Position der Spieler
 * komponente an den Navi übergeben, der dann ermittelt in welcher Richtung der
 * Raum verlassen wird
 * 
 * Direction exitdirection = changecCurrentRoomByDirection(Point2D playerpos)
 * 
 * ----------------------------------------------------------------------------
 * 
 * Sollte die Direction dieser Methode nich null sein, wurde der Raum gewechselt
 * und man kann sich über die Methode den neuen aktuellen Raum ausgeben lassen
 * 
 * nav.getCurrentRoom().getRoomData(); ...
 * 
 * Außerdem ist es möglich die Koordinaten der Player Componente
 * ansttsprechend zu aktualisieren
 * 
 * Point2D newcoord = getEntryCoords(exitdirection);
 * playerEntity.getComponent(PositionComponent.class). setValue(newcoord);
 * 
 * ----------------------------------------------------------------------------
 * 
 * @author Roman
 *
 */
public class MapNavigator {

	private HashMap<Direction, Point2D> entryCoords;

	private LevelData currentLevel;
	private RoomData currentRoom;
	
	private boolean levelChangeLock;

	private static MapNavigator instance;

	private MapNavigator() {

		entryCoords = new HashMap<Direction, Point2D>();

		// Entry points for level change
		entryCoords.put(Direction.NORTH, new Point2D(Settings.getWindowWidth() / 2, Settings.getBlocksize() * 2.5));
		
		entryCoords.put(Direction.SOUTH, new Point2D(Settings.getWindowWidth() / 2,
				Settings.getWindowHeightwithoutGUI() - Settings.getBlocksize() * 2.5));
		
		entryCoords.put(Direction.WEST, new Point2D(Settings.getBlocksize() * 2.5, Settings.getWindowHeightwithoutGUI() / 2));
		
		entryCoords.put(Direction.EAST, new Point2D(Settings.getWindowWidth() - Settings.getBlocksize() * 2.5,
				Settings.getWindowHeightwithoutGUI() / 2));
	}

	public static MapNavigator getInstance() {
		if (MapNavigator.instance == null) {
			MapNavigator.instance = new MapNavigator();
		}
		return MapNavigator.instance;
	}
	
	public void unlock() 
	{
		this.levelChangeLock = false;
	}
	
	public boolean isLocked() 
	{
		return levelChangeLock;
	}

	/**
	 * @param lvl
	 */
	public void changeLevel(LevelData lvl) {
		currentLevel = lvl;
	}

	/**
	 * @param room
	 */
	public void changecCurrentRoom(UUID room) {

		currentRoom = currentLevel.getLevelRoomList().get(room);		
		
	}

	/**
	 * player traveres onto the next room
	 * 
	 * @param playerpos
	 */
	public Direction changecCurrentRoomByDirection(Point2D playerpos) {

		Direction leavingDirection = null;

		if (playerIsNorth(playerpos)) {
			leavingDirection = Direction.NORTH;
		} else if (playerIsSouth(playerpos)) {
			leavingDirection = Direction.SOUTH;
		} else if (playerIsWest(playerpos)) {
			leavingDirection = Direction.WEST;
		} else if (playerIsEasth(playerpos)) {
			leavingDirection = Direction.EAST;
		}

		UUID nextRoom = currentRoom.getadjacentRoom(leavingDirection);

		if (nextRoom != new UUID(0, 0)) {
			changecCurrentRoom(nextRoom);
			 levelChangeLock = true;
			
			return leavingDirection;
			
			
		}

		return null;
	}

	/**
	 * @param
	 */
	public LevelData getLevel() {

		return currentLevel;
	}

	public RoomData getCurrentRoom() {
		return this.currentRoom;
	}

	/**
	 * traverses room grid and picks first non empty room as starting room
	 * 
	 * @param
	 */
	public boolean setStartingRoom() {
		UUID[][] roomGrid = this.currentLevel.getLevelRoomGrid();

		for (int i = 0; i < roomGrid.length; i++) {
			for (int j = 0; j < roomGrid[i].length; j++) {
				if (roomGrid[i][j] == null) {
					// System.out.print((new UUID(0, 0)).toString().substring(0, 4) + " ");
				} else {
					currentRoom = currentLevel.getLevelRoomList().get(roomGrid[i][j]);
					System.out.print(currentRoom.getRoomID().toString());
					return true;
					
				}
			}
		}

		return false;
	}

	/**
	 * player redraw position on room change
	 * 
	 * @param dir leaving direction of previous room
	 * @return
	 */
	public Point2D getEntryCoords(Direction dir) {

		Direction counterdirection = null;

		switch (dir) {
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
		
		return entryCoords.get(counterdirection);

	}

	/**
	 * checks if player is in range of northern exit
	 * 
	 * @param playerpos
	 * @return
	 */
	private boolean playerIsNorth(Point2D playerpos) {
		
//		System.out.println("x: " + playerpos.getX());
//		System.out.println("x: " + playerpos.getY());
//		System.out.println("width: " + Settings.getWindowWidth());
//		System.out.println("height: " + Settings.getWindowHeight());
		
		return (playerpos.getY()  < (Settings.getWindowHeightwithoutGUI()/2)) &&
			   (playerpos.getX()  > (Settings.getWindowWidth()/4)) &&
			   (playerpos.getX()  < ((Settings.getWindowWidth() - (Settings.getWindowWidth()/4))));
		
		
	}

	/**
	 * checks if player is in range of southern exit
	 * 
	 * @param playerpos
	 * @return
	 */
	private boolean playerIsSouth(Point2D playerpos) {
		
		return (playerpos.getY()  > (Settings.getWindowHeightwithoutGUI()/2)) &&
				   (playerpos.getX()  > (Settings.getWindowWidth()/4)) &&
				   (playerpos.getX()  < ((Settings.getWindowWidth() - (Settings.getWindowWidth()/4))));
	}

	/**
	 * checks if player is in range of western exit
	 * 
	 * @param playerpos
	 * @return
	 */
	private boolean playerIsWest(Point2D playerpos) {
		return (playerpos.getX() < (Settings.getWindowWidth()/4));
	}

	/**
	 * checks if player is in range of eastern exit
	 * 
	 * @param playerpos
	 * @return
	 */
	private boolean playerIsEasth(Point2D playerpos) {
		return (playerpos.getX()  > ((Settings.getWindowWidth() - (Settings.getWindowWidth()/4))));
	}
	
	
	
	public void setRoomVisited(UUID room) {
		
		currentLevel.getLevelRoomList().get(room).setisVisited(true);
		
	}
	
	/**
	 * 
	 * 
	 * @param 
	 * @return
	 */
	public boolean getRoomVisited() {
		
		return currentLevel.getLevelRoomList().get(this.getCurrentRoom().getRoomID()).getisVisited();
		
	}
	

}
