package client;

import shared.domain.AttendRequest;
import shared.domain.Event;
import shared.domain.Invoice;
import shared.domain.InvoiceReply;
import shared.messaging.Destinations;
import shared.messaging.receiving.queue.QueueReceiveGateway;
import shared.messaging.receiving.topic.TopicReceiveGateway;
import shared.messaging.sending.queue.QueueSendGateway;

import java.util.List;

public class ClientLogic {
    private ClientData clientData;
    private String email;
    private String subscriptionName;
    private String selector;

    private QueueSendGateway<InvoiceReply> invoiceReplySendGateway;
    private QueueReceiveGateway<Invoice> invoiceReceiveGateway;
    private QueueSendGateway<AttendRequest> attendRequestSendGateway;
    private TopicReceiveGateway<Event> eventReceiveGateway;

    public ClientLogic(String email) {
        this.email = email;
        this.subscriptionName = email + "EventSubscription";
        this.selector = "email = '"+email+"'";
        this.clientData = new ClientData();
        initGateways();
    }

    private void initGateways(){
        invoiceReplySendGateway = new QueueSendGateway<>(Destinations.INVOICE_REPLY);
        attendRequestSendGateway = new QueueSendGateway<>(Destinations.ATTEND_EVENT);

        eventReceiveGateway = new TopicReceiveGateway<Event>(Event.class, Destinations.EVENT, email, subscriptionName){
            @Override
            public void parseMessage(Event event, String correlationId) {
                clientData.parseEvent(event, correlationId);
            }
        };
        invoiceReceiveGateway = new QueueReceiveGateway<Invoice>(Invoice.class, Destinations.INVOICE, selector){
            @Override
            public void parseMessage(Invoice invoice, String correlationId) {
                clientData.parseInvoice(invoice, correlationId);
            }
        };
    }

    public void sendAttendRequest(int eventIndex) {
        attendRequestSendGateway.createMessage(new AttendRequest(clientData.getEvent(eventIndex).getEventId(), email));
        attendRequestSendGateway.sendMessage();
        clientData.removeEvent(eventIndex);
    }

    public boolean listEvents() {
        if(!clientData.eventsIsEmpty()){
            List<Event> events = clientData.getEvents();
            for(Event e : events){
                System.out.println(events.indexOf(e) +". "+ e.info());
            }
            return true;
        }
        return false;
    }

    public boolean listInvoices() {
        if(!clientData.invoicesIsEmpty()){
            List<Invoice> invoices = clientData.listInvoices();
            for(Invoice i : invoices){
                System.out.println(invoices.indexOf(i) +". "+ i.info());
            }
            return true;
        }
        return false;
    }

    public void sendInvoiceReply(int invoiceIndex) {
        Invoice invoice = clientData.getInvoice(invoiceIndex);
        invoiceReplySendGateway.createMessage(new InvoiceReply(invoice.getInvoiceNr(), email));
        invoiceReplySendGateway.sendMessage();
        clientData.removeInvoice(invoiceIndex);
    }
}
