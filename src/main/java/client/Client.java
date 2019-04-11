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
        String selector = "email = '"+email+"'";

        ClientParser clientParser = new ClientParser();

        TopicReceiveGateway<Event> eventReceiveGateway = new TopicReceiveGateway<Event>(Destinations.EVENT, email, subscriptionName){
            @Override
            public void parseMessage(Event event, String correlationId) {
                clientParser.parseEvent(event, correlationId);
            }
        };
        QueueSendGateway<AttendRequest> attendRequestSendGateway = new QueueSendGateway<>(Destinations.ATTEND_EVENT);
        QueueReceiveGateway<Invoice> invoiceReceiveGateway = new QueueReceiveGateway<Invoice>(Destinations.INVOICE, selector){
            @Override
            public void parseMessage(Invoice invoice, String correlationId) {
                clientParser.parseInvoice(invoice, correlationId);
            }
        };

        QueueSendGateway<InvoiceReply> invoiceReplySendGateway = new QueueSendGateway<>(Destinations.INVOICE_REPLY);

        //user interface
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
                        clientParser.removeEvent(eventIndex);
                    }else {
                        System.out.println("currently no events");
                    }
                    break;
                case "invoice":
                    if(!clientParser.invoicesIsEmpty()){
                        clientParser.listInvoices();
                        System.out.println("enter the number of the invoice to pay");
                        int invoiceIndex = Integer.valueOf(scanner.nextLine());
                        Invoice invoice = clientParser.getInvoice(invoiceIndex);
                        invoiceReplySendGateway.createMessage(new InvoiceReply(invoice.getInvoiceNr(), email));
                        invoiceReplySendGateway.sendMessage();
                        clientParser.removeInvoice(invoiceIndex);
                    } else {
                        System.out.println("currently no invoices");
                    }

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
