package shared.messaging.sending.queue;

import shared.messaging.Destinations;
import shared.messaging.sending.SendMessageService;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;

public class QueueSendMessageService extends SendMessageService {
    public QueueSendMessageService(Destinations outgoing) {
        super();
        try {
            destination = session.createQueue(outgoing.toString());

            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
