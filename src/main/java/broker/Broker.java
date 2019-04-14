package broker;

import org.joda.time.LocalTime;
import java.util.Scanner;

public class Broker {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        BrokerLogic brokerLogic = new BrokerLogic();

        System.out.println("The current local time is: " + currentTime);
        System.out.println("Welcome to the broker of the event system");

        //todo receive invoice replies
        //todo remove events when invoices send, via topic
        //todo something when event is full, for the client

        Scanner scanner = new Scanner(System.in);
        //user interface
        boolean doing = true;
        do {
            System.out.println("please enter \"list\" to view events and send invoices, type \"stop\" to stop the broker.");
            String switchString = scanner.nextLine();
            switch (switchString) {
                case "list":
                    brokerLogic.listEvents();
                    System.out.println("enter the event number to send invoices to");
                    int eventIndex = Integer.valueOf(scanner.nextLine());
                    brokerLogic.sendInvoicesForEvent(eventIndex);

                    break;
                case "stop":
                    doing = false;
                    break;
                default:
                    System.out.println("please type either list or stop");
                    break;
            }
        } while (doing);
    }
}
