package eventMaker;

import org.joda.time.LocalTime;
import shared.domain.Event;
import shared.messaging.QueueApplicationGateway;
import shared.messaging.Destinations;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Scanner;

public class EventMakerClient {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        EventParser eventParser = new EventParser();
        QueueApplicationGateway<Event, Event> eventGateway = new QueueApplicationGateway(Destinations.NEW_EVENT, Destinations.EVENT){
            @Override
            public void parseMessage(Serializable object, String correlationId) {
                eventParser.parseEvent((Event) object, correlationId);
            }
        };
        System.out.println("The current local time is: " + currentTime);

        System.out.println("welcome to the event maker client");

        Scanner scanner = new Scanner(System.in);

        //create and send an event
        try{
            Event event = eventParser.createEvent(scanner);
            System.out.println("Sending: "+event.toString());
            eventGateway.createMessage(event);
            eventGateway.sendMessage();
        } catch (ParseException e){
            System.out.println("write a proper date next time");
        }

        String end = scanner.nextLine();
        scanner.close();
    }




}
