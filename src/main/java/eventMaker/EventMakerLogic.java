package eventMaker;

import shared.domain.Event;
import shared.messaging.Destinations;
import shared.messaging.receiving.topic.TopicReceiveGateway;
import shared.messaging.sending.queue.QueueSendGateway;


public class EventMakerLogic {

    private String clientId;
    private String subscriptionName;
    private QueueSendGateway<Event> eventSendGateway;
    private TopicReceiveGateway<Event> eventReceiveGateway;

    public EventMakerLogic(String clientId) {
        this.clientId = clientId;
        this.subscriptionName = clientId + "eventSubscription";
        initGateways();
    }

    private void initGateways(){
        eventSendGateway = new QueueSendGateway<>(Destinations.NEW_EVENT);
        eventReceiveGateway = new TopicReceiveGateway<Event>(Destinations.EVENT, clientId, subscriptionName) {
            @Override
            public void parseMessage(Event event, String correlationId) {
                parseEvent(event, correlationId);
            }
        };
    }

    public void parseEvent(Event event, String correlationId) {
        //todo add to memory and check for updated events or something.
        System.out.println("Received: "+ event.info());
    }

    public void sendNewEvent(Event event) {
        System.out.println("Sending: " + event.info());
        eventSendGateway.createMessage(event);
        eventSendGateway.sendMessage();
    }
}
