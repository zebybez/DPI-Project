package client;

import shared.domain.Event;
import shared.domain.Invoice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientParser {

    private List<Event> events = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();

    public void parseEvent(Event event, String correlationId) {
        System.out.println("Received: " + event.info());
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

    public void removeEvent(int index){
        events.remove(index);
    }

    public boolean eventsIsEmpty(){
        return 0 == events.size();
    }

    public void parseInvoice(Invoice invoice, String correlationId) {
        System.out.println("Received " + invoice.info());

    }

    public void listInvoices(){
        for(Invoice e : invoices){
            System.out.println(invoices.indexOf(e) + e.info());
        }
    }

    public Invoice getInvoice(int index){
        return invoices.get(index);
    }

    public boolean invoicesIsEmpty(){
        return 0 == invoices.size();
    }
}
