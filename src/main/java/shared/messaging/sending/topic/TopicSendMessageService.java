package shared.messaging.sending.topic;

import shared.messaging.Destinations;
import shared.messaging.sending.SendMessageService;

import javax.jms.*;

public class TopicSendMessageService extends SendMessageService {

    public TopicSendMessageService(Destinations outgoing) {
        super();
        try {
            destination = session.createTopic(outgoing.toString());

            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
