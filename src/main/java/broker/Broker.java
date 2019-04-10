package broker;

import org.joda.time.LocalTime;
import shared.domain.AttendRequest;
import shared.domain.Event;
import shared.exceptions.TooManyAttendeesException;
import shared.messaging.Destinations;
import shared.messaging.receiving.queue.QueueReceiveGateway;
import shared.messaging.sending.topic.TopicSendGateway;

public class Broker {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        BrokerParser brokerParser = new BrokerParser();
        System.out.println("The current local time is: " + currentTime);
        System.out.println("Welcome to the broker of the event system");

        TopicSendGateway<Event> eventSendTopicGateway = new TopicSendGateway<>(Destinations.EVENT);
        QueueReceiveGateway<Event> newEventReceiveGateway = new QueueReceiveGateway<Event>(Destinations.NEW_EVENT){
            @Override
            public void parseMessage(Event event, String correlationId) {
                brokerParser.parseNewEvent(event, correlationId);
                eventSendTopicGateway.createMessage(event);
                eventSendTopicGateway.sendMessage();
            }
        };

        QueueReceiveGateway<AttendRequest> attendRequestReceiveGateway = new QueueReceiveGateway<AttendRequest>(Destinations.ATTEND_EVENT){
            @Override
            public void parseMessage(AttendRequest attendRequest, String correlationId) {
                try{
                    brokerParser.parseAttendRequest(attendRequest, correlationId);
                } catch (TooManyAttendeesException e){
                    System.out.println("cannot add attendee to event, event is full");
                }

            }
        };

    }
}
