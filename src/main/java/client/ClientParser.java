package client;

import shared.domain.Event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientParser {

    List<Event> events = new ArrayList();

    public void parseEvent(Event event, String correlationId) {
        System.out.println("received: " + event.info());
        events.add(event);
    }

    public void listEvents(){
        for(Event e : events){
            System.out.println(events.indexOf(e) + e.info());
        }
    }

    public Event getEvent(int index){
        return events.get(index);
    }

    public boolean eventsIsEmpty(){
        return 0 == events.size();
    }
}
