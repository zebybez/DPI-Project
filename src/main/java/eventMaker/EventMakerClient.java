package eventMaker;

import org.joda.time.LocalTime;
import shared.domain.Event;
import shared.messaging.Destinations;
import shared.messaging.receiving.topic.TopicReceiveGateway;
import shared.messaging.sending.queue.QueueSendGateway;

import java.text.ParseException;
import java.util.Scanner;

public class EventMakerClient {

    public static void main(String[] args) {
        //setup client identity and classes

        LocalTime currentTime = new LocalTime();
        EventMakerLogic eventMakerLogic = new EventMakerLogic("mainClient");

        //welcome msg
        System.out.println("the current time is: " + currentTime);
        System.out.println("welcome to the event maker client");
        //user interface
        Scanner scanner = new Scanner(System.in);
        boolean doing = true;
        do {
            System.out.println("type new, update or stop");
            String switchString = scanner.nextLine();
            switch (switchString) {
                case "new":            //create and send an event
                    try {
                        Event event = eventMakerLogic.createEvent(scanner);
                        eventMakerLogic.sendNewEvent(event);
                    } catch (ParseException e) {
                        System.out.println("write a proper date next time");
                    }
                    break;
                case "update":
                    System.out.println("not implemented yet");
                    break;
                case "stop":
                    doing = false;
                    break;
                default:
                    System.out.println("unrecognised input");
                    break;
            }

        } while (doing);

    }


}
