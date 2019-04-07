package eventMaker;

import org.joda.time.LocalTime;
import shared.domain.Event;
import shared.messaging.QueueApplicationGateway;
import shared.messaging.Destinations;
import shared.messaging.TopicApplicationGateway;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Scanner;

public class EventMakerClient {

    public static void main(String[] args) {
        String clientId = "mainClient";
        String subscriptionName = "mainClient";
        LocalTime currentTime = new LocalTime();
        EventParser eventParser = new EventParser();
        QueueApplicationGateway<Event, Event> eventSendGateway = new QueueApplicationGateway(Destinations.NEW_EVENT, Destinations.NO_DESTINATION);
        TopicApplicationGateway<Event, Event> eventReceiveGateway = new TopicApplicationGateway(Destinations.NO_DESTINATION, Destinations.EVENT, clientId, subscriptionName){
            @Override
            public void parseMessage(Serializable object, String correlationId) {
                eventParser.parseEvent((Event) object, correlationId);
            }
        };
        System.out.println("The current local time is: " + currentTime);

        System.out.println("welcome to the event maker client");

        Scanner scanner = new Scanner(System.in);



        do{
            //create and send an event
            try{
                Event event = eventParser.createEvent(scanner);
                System.out.println("Sending: "+ event.info());
                eventSendGateway.createMessage(event);
                eventSendGateway.sendMessage();
            } catch (ParseException e){
                System.out.println("write a proper date next time");
            }
        } while (true);

    }





}
