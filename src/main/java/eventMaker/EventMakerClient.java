package eventMaker;

import org.joda.time.LocalTime;
import shared.domain.Event;
import shared.messaging.receiving.queue.QueueReceiveGateway;
import shared.messaging.Destinations;
import shared.messaging.receiving.topic.TopicReceiveGateway;
import shared.messaging.sending.queue.QueueSendGateway;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Scanner;

public class EventMakerClient {

    public static void main(String[] args) {
        //setup client identity and classes
        String clientId = "mainClient";
        String subscriptionName = "mainClient";
        LocalTime currentTime = new LocalTime();
        EventParser eventParser = new EventParser();
        QueueSendGateway<Event> eventSendGateway = new QueueSendGateway<>(Destinations.NEW_EVENT);
        TopicReceiveGateway<Event> eventReceiveGateway = new TopicReceiveGateway<Event>(Destinations.EVENT, clientId, subscriptionName){
            @Override
            public void parseMessage(Event event, String correlationId) {
                eventParser.parseEvent(event, correlationId);
            }
        };

        //welcome msg
        System.out.println("the current time is: "+ currentTime);
        System.out.println("welcome to the event maker client");


        //todo update events
        //user interface
        Scanner scanner = new Scanner(System.in);
        boolean doing = true;
        do{
            System.out.println("type create or stop");
            String switchString = scanner.nextLine();
            switch(switchString){
                case"create":
                    //create and send an event
                    try{
                        Event event = eventParser.createEvent(scanner);
                        System.out.println("Sending: "+ event.info());
                        eventSendGateway.createMessage(event);
                        eventSendGateway.sendMessage();
                    } catch (ParseException e){
                        System.out.println("write a proper date next time");
                    }
                    break;
                case"stop":
                    doing = false;
                    break;
                    default:
                        System.out.println("type either stop or create");
                        break;
            }

        } while (doing);

    }





}
