package client;

import eventMaker.EventParser;
import org.joda.time.LocalTime;
import shared.domain.Event;
import shared.messaging.Destinations;
import shared.messaging.QueueApplicationGateway;
import shared.messaging.TopicApplicationGateway;

import java.io.Serializable;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        System.out.println("The current local time is: " + currentTime);

        //setup the client identity
        Scanner scanner = new Scanner(System.in);
        System.out.println("what is my id?");
        String clientId = scanner.nextLine();
        System.out.println("what is the subscription name for receiving events");
        String subscriptionName = scanner.nextLine();

        ClientParser clientParser = new ClientParser();

        TopicApplicationGateway<Event, Event> eventReceiveGateway = new TopicApplicationGateway(Destinations.NO_DESTINATION, Destinations.EVENT, clientId, subscriptionName){
            @Override
            public void parseMessage(Serializable object, String correlationId) {
                clientParser.parseEvent((Event) object, correlationId);
            }
        };


    }
}
