package events;

import javafx.event.Event;
import javafx.event.EventType;

import java.util.HashMap;

public class GameEvent extends Event {
	
    public static final EventType<GameEvent> ANY = new EventType<>(Event.ANY, "GAME_EVENT");
    
    public static final EventType<GameEvent> OUT_OF_WORLD = new EventType<>(ANY, "OUT_OF_WORLD");
    
    //general
    public static final EventType<GameEvent> COLLISION = new EventType<>(ANY, "COLLISION");
    public static final EventType<GameEvent> DEATH_PLAYER = new EventType<>(ANY, "COLLISION");
    public static final EventType<GameEvent> DEATH_ENEMY= new EventType<>(ANY, "COLLISION");
    
    //item pick up
    public static final EventType<GameEvent> COLLISION_PLAYER_ITEM = new EventType<>(ANY, "COLLISION");      
    public static final EventType<GameEvent> PLAYER_UPGRADE_STATS = new EventType<>(ANY, "COLLISION_PLAYER_ENEMY");    
    
    //Events Player/player projectiles collides with enemy
    public static final EventType<GameEvent> COLLISION_PLAYER_ENEMY = new EventType<>(ANY, "COLLISION_PLAYER_ENEMY");
    public static final EventType<GameEvent> PLAYER_SHOT = new EventType<>(ANY, "ENTITY_JUMP");
    public static final EventType<GameEvent> COLLISION_PROJECTILE_PLAYER_ENEMY = new EventType<>(ANY, "COLLISION_PLAYER_ENEMY"); 
    
    //Events enemy(projectile) collides with player
    public static final EventType<GameEvent> COLLISION_ENEMY_PLAYER = new EventType<>(ANY, "COLLISION_PLAYER_ENEMY");
    public static final EventType<GameEvent> ENEMY_SHOT = new EventType<>(ANY, "ENTITY_LAND");
    public static final EventType<GameEvent> COLLISION_PROJECTILE_ENEMY_PLAYER = new EventType<>(ANY, "COLLISION_PLAYER_ENEMY");
    
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
