package broker;

import org.joda.time.LocalTime;
import shared.domain.AttendRequest;
import shared.domain.Event;
import shared.domain.Invoice;
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
    private BrokerData brokerData;


    public BrokerLogic() {
        brokerData = new BrokerData();
        initGateways();

    }

    private void initGateways(){
        invoiceSendGateway = new QueueSendGateway<>(Destinations.INVOICE);
        eventSendTopicGateway = new TopicSendGateway<>(Destinations.EVENT);
        newEventReceiveGateway = new QueueReceiveGateway<Event>(Event.class, Destinations.NEW_EVENT) {
            @Override
            public void parseMessage(Event event, String correlationId) {
                parseNewEvent(event);
            }
        };

        attendRequestReceiveGateway = new QueueReceiveGateway<AttendRequest>(AttendRequest.class, Destinations.ATTEND_EVENT) {
            @Override
            public void parseMessage(AttendRequest attendRequest, String correlationId) {
                    parseAttendRequest(attendRequest);
            }
        };
    }

    public void parseNewEvent(Event event) {
        brokerData.parseNewEvent(event, "");
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
            invoiceSendGateway.createMessage(new Invoice(email, event.getEventId(), brokerData.nextNumber(), event.getPrice(), new LocalTime().toDateTimeToday().toDate()));
            invoiceSendGateway.setStringProperty("email", email);
            invoiceSendGateway.sendMessage();
            System.out.println("Send: invoice to " + email);
        }
    }
}
