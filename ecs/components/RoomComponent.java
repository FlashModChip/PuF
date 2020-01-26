package components;

import javafx.geometry.Point2D;

import java.util.List;
import java.util.UUID;



/**
 * position component
 */
public class RoomComponent extends Component<UUID> {
    
	private UUID spawnedInRoom;

    public RoomComponent() {
        this.spawnedInRoom = new UUID(0, 0);
    }
    
    public RoomComponent(UUID room ) {
        this.spawnedInRoom = room;
    }


    @Override
    public UUID getValue() {
        return spawnedInRoom;
    }

    @Override
    public void setValue(UUID value) {
        this.spawnedInRoom = value;
    }

	
}
