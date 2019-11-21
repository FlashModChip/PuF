package events;

import javafx.event.Event;
import javafx.event.EventType;

import java.util.HashMap;

public class GameEvent extends Event {
    public static final EventType<GameEvent> ANY = new EventType<>(Event.ANY, "GAME_EVENT");
    public static final EventType<GameEvent> OUT_OF_WORLD = new EventType<>(ANY, "OUT_OF_WORLD");
    public static final EventType<GameEvent> COLLISION = new EventType<>(ANY, "COLLISION");
    public static final EventType<GameEvent> ENTITY_JUMP = new EventType<>(ANY, "ENTITY_JUMP");
    public static final EventType<GameEvent> ENTITY_LAND = new EventType<>(ANY, "ENTITY_LAND");

    private HashMap<EventData, Object> data = new HashMap<>();

    public GameEvent(EventType<GameEvent> type) {
        super(type);
    }

    public void addData(EventData dataType, Object object) {
        data.put(dataType, object);
    }

    public Object getData(EventData dataType) {
        return data.get(dataType);
    }

    public HashMap<EventData, Object> getAllData() {
        return data;
    }
}
