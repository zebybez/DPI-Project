package broker;

import shared.domain.AttendRequest;
import shared.domain.Event;
import shared.domain.Invoice;
import shared.domain.InvoiceReply;
import shared.exceptions.TooManyAttendeesException;

import java.util.*;

public class BrokerData {

    Map<String, Event> eventMap = new HashMap<>();
    Map<String, List<String>> attendeeMap = new HashMap<>();
    Map<Integer, String> invoicesForEventMap = new HashMap<>();

    int invoiceCounter = 0;

    public void parseNewEvent(Event event){

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

    public Collection<Event> getEventList() {
        return eventMap.values();
    }

    public void saveInvoice(Invoice invoice)
    {
        int invoiceNr = invoice.getInvoiceNr();
        String eventNr = invoice.getEventId();
        invoicesForEventMap.put(invoiceNr, eventNr);
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
        return ++invoiceCounter;
    }

    public String payInvoice(InvoiceReply invoiceReply) {
        return invoicesForEventMap.remove(invoiceReply.getInvoiceNr());
    }

    public boolean hasPendingInvoices(String eventNr) {
        return invoicesForEventMap.values().contains(eventNr);
    }
}
