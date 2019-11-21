package events;

import javafx.event.EventType;

public interface EventNotifierInterface {
    void fireEvent(GameEvent gameEvent);

    void addEventhandler(EventType<GameEvent> gameEvent, GameEventObserver handler);
    void removeEventhandler(EventType<GameEvent> gameEvent, GameEventObserver handler);

}
