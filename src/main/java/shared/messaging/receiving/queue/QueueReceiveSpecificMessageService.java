package shared.messaging.receiving.queue;

import shared.messaging.Destinations;
import shared.messaging.receiving.ReceiveMessageService;

import javax.jms.JMSException;
import javax.jms.MessageListener;

public class QueueReceiveSpecificMessageService extends ReceiveMessageService {
    public QueueReceiveSpecificMessageService(Destinations incoming, String selector, MessageListener messageListener) {
        super();
        try {
            recievedestination = session.createQueue(incoming.toString());

            consumer = session.createConsumer(recievedestination, selector);
            consumer.setMessageListener(messageListener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
