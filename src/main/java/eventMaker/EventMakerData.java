package eventMaker;

import shared.domain.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventMakerData {

    Map<String, Event> eventMap;

    public EventMakerData() {
        this.eventMap = new HashMap<>();
    }

    public boolean contains(String eventId) {
        return eventMap.containsKey(eventId);
    }

    public void saveEvent(Event event) {
        eventMap.put(event.getEventId(), event);
    }

    public Collection<Event> getEventList(){
        return eventMap.values();
    }
}
