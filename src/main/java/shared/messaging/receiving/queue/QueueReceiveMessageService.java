package shared.messaging.receiving.queue;

import shared.messaging.Destinations;
import shared.messaging.receiving.ReceiveMessageService;

import javax.jms.JMSException;
import javax.jms.MessageListener;

public class QueueReceiveMessageService extends ReceiveMessageService {
    public QueueReceiveMessageService(Destinations incoming, MessageListener messageListener) {
        super();
        try {
            recievedestination = session.createQueue(incoming.toString());

            consumer = session.createConsumer(recievedestination);
            consumer.setMessageListener(messageListener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
