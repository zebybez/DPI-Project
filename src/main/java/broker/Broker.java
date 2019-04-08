package broker;

import org.joda.time.LocalTime;
import shared.domain.Event;
import shared.messaging.Destinations;
import shared.messaging.receiving.queue.QueueReceiveGateway;
import shared.messaging.sending.topic.TopicSendGateway;

import java.io.Serializable;

public class Broker {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        BrokerParser brokerParser = new BrokerParser();
        System.out.println("The current local time is: " + currentTime);
        System.out.println("Welcome to the broker of the event system");

        TopicSendGateway<Event> eventSendTopicGateway = new TopicSendGateway(Destinations.EVENT);
        QueueReceiveGateway<Event> newEventReceiveGateway = new QueueReceiveGateway(Destinations.NEW_EVENT){
            @Override
            public void parseMessage(Serializable object, String correlationId) {
                brokerParser.parseNewEvent((Event) object, correlationId);
                eventSendTopicGateway.createMessage((Event)object);
                eventSendTopicGateway.sendMessage();
            }
        };


    }
}
