package broker;

import org.joda.time.LocalTime;
import shared.domain.Event;
import shared.messaging.Destinations;
import shared.messaging.QueueApplicationGateway;
import shared.messaging.TopicApplicationGateway;

import java.io.Serializable;

public class Broker {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        BrokerParser brokerParser = new BrokerParser();
        System.out.println("The current local time is: " + currentTime);
        System.out.println("Welcome to the broker of the event system");

        TopicApplicationGateway<Event, Event> eventSendTopicGateway = new TopicApplicationGateway(Destinations.EVENT, Destinations.NO_DESTINATION, "noclient", "nosubscription");
        QueueApplicationGateway<Event, Event> eventReceiveGateway = new QueueApplicationGateway(Destinations.NO_DESTINATION, Destinations.NEW_EVENT){
            @Override
            public void parseMessage(Serializable object, String correlationId) {
                brokerParser.parseNewEvent((Event) object, correlationId);
                eventSendTopicGateway.createMessage(object);
                eventSendTopicGateway.sendMessage();
            }
        };


    }
}
