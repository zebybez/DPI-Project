package broker;

import org.joda.time.LocalTime;
import shared.domain.AttendRequest;
import shared.domain.Event;
import shared.domain.Invoice;
import shared.domain.InvoiceReply;
import shared.exceptions.TooManyAttendeesException;
import shared.messaging.Destinations;
import shared.messaging.receiving.queue.QueueReceiveGateway;
import shared.messaging.sending.queue.QueueSendGateway;
import shared.messaging.sending.topic.TopicSendGateway;

import java.util.List;

public class BrokerLogic {

    private QueueSendGateway<Invoice> invoiceSendGateway;
    private TopicSendGateway<Event> eventSendTopicGateway;
    private QueueReceiveGateway<Event> newEventReceiveGateway;
    private QueueReceiveGateway<AttendRequest> attendRequestReceiveGateway;
    private QueueReceiveGateway<InvoiceReply> invoiceReplyReceiveGateway;
    private BrokerData brokerData;


    public BrokerLogic() {
        brokerData = new BrokerData();
        initGateways();

    }

    private void initGateways(){
        invoiceSendGateway = new QueueSendGateway<>(Destinations.INVOICE);
        eventSendTopicGateway = new TopicSendGateway<>(Destinations.EVENT_TOPIC);
        newEventReceiveGateway = new QueueReceiveGateway<Event>(Event.class, Destinations.EVENT) {
            @Override
            public void parseMessage(Event event, String correlationId) {
                parseEvent(event);
            }
        };

        attendRequestReceiveGateway = new QueueReceiveGateway<AttendRequest>(AttendRequest.class, Destinations.ATTEND_EVENT) {
            @Override
            public void parseMessage(AttendRequest attendRequest, String correlationId) {
                    parseAttendRequest(attendRequest);
            }
        };
        invoiceReplyReceiveGateway = new QueueReceiveGateway<InvoiceReply>(InvoiceReply.class, Destinations.INVOICE_REPLY) {
            @Override
            public void parseMessage(InvoiceReply invoiceReply, String correlationId) {
                parseInvoiceReply(invoiceReply);
            }
        };
    }

    private void parseInvoiceReply(InvoiceReply invoiceReply) {
        System.out.println("Received: " +invoiceReply.info());
        String eventNr = brokerData.payInvoice(invoiceReply);
        if(!brokerData.hasPendingInvoices(eventNr)){
            System.out.println("all invoices for event "+eventNr+" payed");
        }
    }

    public void parseEvent(Event event) {
        brokerData.parseNewEvent(event);
        System.out.println("Received: "+ event.info());
        eventSendTopicGateway.createMessage(event);
        eventSendTopicGateway.sendMessage();
    }

    public void parseAttendRequest(AttendRequest attendRequest) {
        System.out.println("Received: attendRequest from:" + attendRequest.getEmail());
        try{
            brokerData.parseAttendRequest(attendRequest, "");
        } catch (TooManyAttendeesException e) {
            System.out.println("cannot add attendee to event, event is full");
        }
    }

    public void listEvents() {
        int i = 0;
        for(Event e : brokerData.getEventList()){
            System.out.println(i+". "+e.info());
            i++;
        }
    }

    public void sendInvoicesForEvent(int eventIndex) {
        Event event = brokerData.getEvent(eventIndex);
        List<String> attendees = brokerData.getAttendeesForEvent(event.getEventId());
        for (String email : attendees) {
            Invoice invoice = new Invoice(email, event.getEventId(), brokerData.nextNumber(), event.getPrice(), new LocalTime().toDateTimeToday().toDate());
            invoiceSendGateway.createMessage(invoice);
            invoiceSendGateway.setStringProperty("email", email);
            invoiceSendGateway.sendMessage();
            brokerData.saveInvoice(invoice);
            System.out.println("Send: invoice to " + email);

        }
    }
}
