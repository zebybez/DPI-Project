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

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        System.out.println("The current local time is: " + currentTime);

        //setup the client identity
        Scanner scanner = new Scanner(System.in);
        System.out.println("what is your email?");
        String email = scanner.nextLine();
        ClientLogic clientLogic = new ClientLogic(email);

        //user interface
        boolean doing = true;
        do{
            System.out.println("please type \"attend to attend an event\", or type \"invoice\" to pay events, type \"stop \" to exit the program");
            String switchString = scanner.nextLine();
            switch(switchString){
                case "attend":
                    if(clientLogic.listEvents()){
                        System.out.println("enter the number of the event to attend");
                        int eventIndex = Integer.valueOf(scanner.nextLine());
                        clientLogic.sendAttendRequest(eventIndex);
                    }else {
                        System.out.println("no events at the moment");
                    }

                    break;
                case "invoice":
                    if(clientLogic.listInvoices()){
                        System.out.println("enter the number of the invoice to pay");
                        int invoiceIndex = Integer.valueOf(scanner.nextLine());
                        clientLogic.sendInvoiceReply(invoiceIndex);
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
