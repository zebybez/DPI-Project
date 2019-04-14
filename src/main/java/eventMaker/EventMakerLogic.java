package eventMaker;

import shared.domain.Event;
import shared.messaging.Destinations;
import shared.messaging.receiving.topic.TopicReceiveGateway;
import shared.messaging.sending.queue.QueueSendGateway;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class EventMakerLogic {

    private String clientId;
    private String subscriptionName;
    private QueueSendGateway<Event> eventSendGateway;
    private TopicReceiveGateway<Event> eventReceiveGateway;

    public EventMakerLogic(String clientId) {
        this.clientId = clientId;
        this.subscriptionName = clientId + "eventSubscription";
        initGateways();
    }

    private void initGateways(){
        eventSendGateway = new QueueSendGateway<>(Destinations.NEW_EVENT);
        eventReceiveGateway = new TopicReceiveGateway<Event>(Destinations.EVENT, clientId, subscriptionName) {
            @Override
            public void parseMessage(Event event, String correlationId) {
                parseEvent(event, correlationId);
            }
        };
    }

    public Event createEvent(Scanner scanner) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMYYYY");

        System.out.println("new event: \n name?");
        String name = scanner.nextLine();
        System.out.println("price?");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("max attendees?");
        int maxAttendees = Integer.parseInt(scanner.nextLine());
        System.out.println("date? (ddmmyyyy)");
        String dateString = scanner.nextLine();
        Date date = dateFormatter.parse(dateString);

        return new Event(UUID.randomUUID().toString(), name, date, price,maxAttendees);
    }

    public void parseEvent(Event event, String correlationId) {
        //todo add to memory and check for updated events or something.
        System.out.println("Received: "+ event.info());
    }

    public void sendNewEvent(Event event) {
        System.out.println("Sending: " + event.info());
        eventSendGateway.createMessage(event);
        eventSendGateway.sendMessage();
    }
}
