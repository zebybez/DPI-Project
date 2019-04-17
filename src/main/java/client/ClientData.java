package client;

import shared.domain.Event;
import shared.domain.Invoice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientData {

    private List<Event> events = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();

    public void parseEvent(Event event, String correlationId) {
        Event toRemove = null;
        for(Event e : events){
            if(e.getEventId() == event.getEventId()){
                System.out.println("Received update: " + event.info());
                toRemove = e;
            }else {
                //todo elseif event.maxattendees <= 0 : remove event
                System.out.println("Received: " + event.info());
            }
        }
        if(toRemove == null){
            events.add(event);
        }else {
            events.remove(toRemove);
            events.add(event);
        }
    }

    public List<Event> getEvents(){
        return new ArrayList<>(events);
    }

    public Event getEvent(int index){
        return events.get(index);
    }

    public void removeEvent(int index){
        events.remove(index);
    }

    public boolean eventsIsEmpty(){
        return 0 == events.size();
    }

    public void parseInvoice(Invoice invoice, String correlationId) {
        System.out.println("Received " + invoice.info());
        invoices.add(invoice);
    }

    public List<Invoice> listInvoices(){
        return new ArrayList<>(invoices);
    }

    public Invoice getInvoice(int index){
        return invoices.get(index);
    }

    public boolean invoicesIsEmpty(){
        return 0 == invoices.size();
    }

    public void removeInvoice(int invoiceIndex) {
        invoices.remove(invoiceIndex);
    }
}
