package eventMaker;

import org.joda.time.LocalTime;
import shared.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

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
                        Event event = createEvent(scanner);
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

    private static Event createEvent(Scanner scanner) throws ParseException {
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

}
