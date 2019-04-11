package broker;

import shared.domain.AttendRequest;
import shared.domain.Event;
import shared.exceptions.TooManyAttendeesException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrokerParser {

    Map<String, Event> eventMap = new HashMap<>();
    Map<String, List<String>> attendeeMap = new HashMap<>();

    int counter = 0;

    public void parseNewEvent(Event event, String correlationId){

        String eventId = event.getEventId();
        if(!eventMap.containsKey(eventId)){     //add new event
            eventMap.put(eventId, event);
            attendeeMap.put(event.getEventId(), new ArrayList<>());
        } else {                                //update existing event
            eventMap.put(eventId, event);
        }

    }

    public void parseAttendRequest(AttendRequest attendRequest, String correlationId) throws TooManyAttendeesException{

        String eventId =attendRequest.getEventId();
        int max = eventMap.get(eventId).getMaxAttendees();
        List toAddTo = attendeeMap.get(attendRequest.getEventId());
        if(max > toAddTo.size()){
            toAddTo.add(attendRequest.getEmail());
            attendeeMap.put(eventId, toAddTo);
        }
        else throw new TooManyAttendeesException();
    }

    public void listEvents() {
        int i = 0;
        for(Event e : eventMap.values()){
            System.out.println("Nr-"+i+": "+e.info());
            i++;
        }
    }



    public Event getEvent(int eventIndex) {
        int i = 0;
        for(Event e : eventMap.values()){
            if(i == eventIndex){
                return e;
            }
        }
        return null;
    }

    public List<String> getAttendeesForEvent(String eventId) {
        return attendeeMap.get(eventId);
    }

    public int nextNumber() {
        return ++counter;
    }
}
