package broker;

import org.joda.time.LocalTime;
import shared.domain.AttendRequest;
import shared.domain.Event;
import shared.domain.Invoice;
import shared.exceptions.TooManyAttendeesException;
import shared.messaging.Destinations;
import shared.messaging.receiving.queue.QueueReceiveGateway;
import shared.messaging.sending.queue.QueueSendGateway;
import shared.messaging.sending.topic.TopicSendGateway;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Broker {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        BrokerParser brokerParser = new BrokerParser();
        Scanner scanner = new Scanner(System.in);
        System.out.println("The current local time is: " + currentTime);
        System.out.println("Welcome to the broker of the event system");

        TopicSendGateway<Event> eventSendTopicGateway = new TopicSendGateway<>(Destinations.EVENT);
        QueueReceiveGateway<Event> newEventReceiveGateway = new QueueReceiveGateway<Event>(Destinations.NEW_EVENT){
            @Override
            public void parseMessage(Event event, String correlationId) {
                brokerParser.parseNewEvent(event, correlationId);
                eventSendTopicGateway.createMessage(event);
                eventSendTopicGateway.sendMessage();
            }
        };

        QueueReceiveGateway<AttendRequest> attendRequestReceiveGateway = new QueueReceiveGateway<AttendRequest>(Destinations.ATTEND_EVENT){
            @Override
            public void parseMessage(AttendRequest attendRequest, String correlationId) {
                try{
                    brokerParser.parseAttendRequest(attendRequest, correlationId);
                } catch (TooManyAttendeesException e){
                    System.out.println("cannot add attendee to event, event is full");
                }

            }
        };

        QueueSendGateway<Invoice> invoiceSendGateway = new QueueSendGateway<>(Destinations.INVOICE);

        boolean doing = true;
        do{
            System.out.println("usually sending invoices would be automatic, but please enter \"list\" to view events and send invoices");
            scanner.nextLine();
            brokerParser.listEvents();
            System.out.println("enter the event number to send invoices to");
            int eventIndex = Integer.valueOf(scanner.nextLine());
            Event event = brokerParser.getEventId(eventIndex);
            List<String> attendees = brokerParser.getAttendeesForEvent(event.getEventId());
            for(String email : attendees){
                invoiceSendGateway.createMessage(new Invoice(email, event.getEventId(), brokerParser.nextNumber(), event.getPrice(), new LocalTime().toDateTimeToday().toDate()));
                invoiceSendGateway.setStringProperty("email", email);
                invoiceSendGateway.sendMessage();
            }
        }while (doing);
    }
}
