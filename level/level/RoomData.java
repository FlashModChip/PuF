package level;

import java.util.UUID;

/**
 * @author Roman Room node class for level (tile data layout and 4 connections
 *         to adjacent rooms)
 */
public class RoomData {
	private int[][] roomData;

	private UUID adjacentRoomNorth;
	private UUID adjacentRoomSouth;
	private UUID adjacentRoomWest;
	private UUID adjacentRoomEast;

	private UUID roomID;

	public RoomData() {
		roomData = null;

		adjacentRoomNorth = new UUID(0, 0);
		adjacentRoomSouth = new UUID(0, 0);
		adjacentRoomWest = new UUID(0, 0);
		adjacentRoomEast = new UUID(0, 0);

		roomID = UUID.randomUUID();
	}

	public RoomData(int[][] data, UUID north, UUID east, UUID south, UUID west) {
		roomData = data;

		adjacentRoomNorth = north;
		adjacentRoomSouth = south;
		adjacentRoomWest = west;
		adjacentRoomEast = east;

		roomID = UUID.randomUUID();
	}

	/**
	 * @return
	 */
	public UUID getRoomID() {
		return roomID;
	}

	/**
	 * @return
	 */
	public int getDimensionX() {
		return roomData.length;
	}

	/**
	 * @return
	 */
	public int getDimensionY() {
		return roomData[0].length;
	}

	/**
	 * @return
	 */
	public int[][] getRoomData() {
		return roomData;
	}

	/**
	 * @param val
	 */
	public void setRoomData(int[][] val) {
		this.roomData = val;
	}

	/**
	 * @param room
	 * @param direction
	 */
	public void setadjacentRoom(RoomData room, Direction direction) {
		switch (direction) {
		case NORTH:
			adjacentRoomNorth = room.getRoomID();
			break;

		case SOUTH:
			adjacentRoomSouth = room.getRoomID();
			break;

		case WEST:
			adjacentRoomWest = room.getRoomID();
			break;

		case EAST:
			adjacentRoomEast = room.getRoomID();
			break;

		default:
			break;
		}
	}

	/**
	 * @param direction
	 * @return
	 */
	public UUID getadjacentRoom(Direction direction) {
		switch (direction) {
		case NORTH:
			return adjacentRoomNorth;

		case SOUTH:
			return adjacentRoomSouth;

		case WEST:
			return adjacentRoomWest;

		case EAST:
			return adjacentRoomEast;
		}

		return new UUID(0, 0);
	}

	/**
	 * @param roomID
	 * @return
	 */
	public boolean alreadyadjacent(UUID roomID) {
		return this.adjacentRoomNorth == roomID || this.adjacentRoomEast == roomID || this.adjacentRoomSouth == roomID
				|| this.adjacentRoomWest == roomID;
	}
}
