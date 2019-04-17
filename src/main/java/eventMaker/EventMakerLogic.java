package eventMaker;

import shared.domain.Event;
import shared.messaging.Destinations;
import shared.messaging.receiving.topic.TopicReceiveGateway;
import shared.messaging.sending.queue.QueueSendGateway;

import java.util.ArrayList;


public class EventMakerLogic {

    private String clientId;
    private String subscriptionName;
    private QueueSendGateway<Event> eventSendGateway;
    private TopicReceiveGateway<Event> eventReceiveGateway;

    private EventMakerData eventMakerData;

    public EventMakerLogic(String clientId) {
        eventMakerData = new EventMakerData();
        this.clientId = clientId;
        this.subscriptionName = clientId + "eventSubscription";
        initGateways();
    }

    public void listEvents() {
        int i = 0;
        for(Event e : eventMakerData.getEventList()){
            System.out.println(i + ". " + e.info());
            i++;
        }
    }

    public String getEventIdFromList(int eventNr) {
        return new ArrayList<>(eventMakerData.getEventList()).get(eventNr).getEventId();
    }

    private void initGateways(){
        eventSendGateway = new QueueSendGateway<>(Destinations.EVENT);
        eventReceiveGateway = new TopicReceiveGateway<Event>(Event.class, Destinations.EVENT_TOPIC, clientId, subscriptionName) {
            @Override
            public void parseMessage(Event event, String correlationId) {
                parseEvent(event, correlationId);
            }
        };
    }

    public void parseEvent(Event event, String correlationId) {
        //todo add to memory and check for updated events or something.
        if(eventMakerData.contains(event.getEventId())){
            eventMakerData.saveEvent(event);
            System.out.println("Received update: "+event.info());
        }else {
            eventMakerData.saveEvent(event);
            System.out.println("Received: "+ event.info());

        }
    }

    public void sendEvent(Event event) {
        System.out.println("Sending: " + event.info());
        eventMakerData.saveEvent(event);
        eventSendGateway.createMessage(event);
        eventSendGateway.sendMessage();
    }
}
