package client;

import org.joda.time.LocalTime;
import shared.domain.AttendRequest;
import shared.domain.Event;
import shared.domain.Invoice;
import shared.domain.InvoiceReply;
import shared.messaging.Destinations;
import shared.messaging.receiving.queue.QueueReceiveGateway;
import shared.messaging.receiving.topic.TopicReceiveGateway;
import shared.messaging.sending.queue.QueueSendGateway;

import java.io.Serializable;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        System.out.println("The current local time is: " + currentTime);

        //setup the client identity
        Scanner scanner = new Scanner(System.in);
        System.out.println("what is your email?");
        String email = scanner.nextLine();
        String subscriptionName = email + "EventSubscription";

        ClientParser clientParser = new ClientParser();

        TopicReceiveGateway<Event> eventReceiveGateway = new TopicReceiveGateway<Event>(Destinations.EVENT, email, subscriptionName){
            @Override
            public void parseMessage(Event event, String correlationId) {
                clientParser.parseEvent(event, correlationId);
            }
        };
        QueueSendGateway<AttendRequest> attendRequestSendGateway = new QueueSendGateway<>(Destinations.ATTEND_EVENT);
        QueueReceiveGateway<Invoice> invoiceReceiveGateway = new QueueReceiveGateway<Invoice>(Destinations.INVOICE){
            @Override
            public void parseMessage(Invoice invoice, String correlationId) {
                clientParser.parseInvoice(invoice, correlationId);
            }
        };

        QueueSendGateway<InvoiceReply> invoiceSendGateway = new QueueSendGateway<>(Destinations.INVOICE_REPLY);


        boolean doing = true;
        do{
            System.out.println("please type \"attend to attend an event\", or type \"invoice\" to pay events, type \"stop \" to exit the program");
            String switchString = scanner.nextLine();
            switch(switchString){
                case "attend":
                    if(!clientParser.eventsIsEmpty()){
                        clientParser.listEvents();
                        System.out.println("enter the number of the event to attend");
                        int eventIndex = Integer.valueOf(scanner.nextLine());
                        attendRequestSendGateway.createMessage(new AttendRequest(clientParser.getEvent(eventIndex).getEventId(), email));
                        attendRequestSendGateway.sendMessage();
                    }else {
                        System.out.println("currently no events");
                    }
                    break;
                case "invoice":
                    System.out.println("not implemented");
                    break;
                case"stop":
                    doing = false;
                    break;
                default:
                    System.out.println("please type either \"attend\" or \"invoice\"");
                    break;
            }
        }while(doing);
    }
}
