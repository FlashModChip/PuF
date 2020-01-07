package events;

/**
 * event data types
 */
public enum EventData {
    CollisionUUID("UUID of collision"),
    ColliderUUID("UUID of collider"),
    VELOCITY("Point2D");

    EventData(String description) {

    }
}
