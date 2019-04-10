package broker;

import shared.domain.AttendRequest;
import shared.domain.Event;
import shared.exceptions.TooManyAttendeesException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BrokerParser {

    Map<String, Event> eventMap;
    Map<String, List<String>> attendeeMap;


    public void parseNewEvent(Event event, String correlationId){
        //todo add to memory and stuff
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
        if(max < toAddTo.size()){
            toAddTo.add(attendRequest.getEmail());
            attendeeMap.put(eventId, toAddTo);
        }
        else throw new TooManyAttendeesException();
    }
}
