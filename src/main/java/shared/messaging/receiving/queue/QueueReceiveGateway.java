package shared.messaging.receiving.queue;

import shared.messaging.Destinations;
import shared.messaging.receiving.ReceiveGateway;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.Serializable;

//todo make special serializer instead of object messages.
public class QueueReceiveGateway<O extends Serializable> extends ReceiveGateway<O> {

    public QueueReceiveGateway(Destinations incoming) {
        receiveMessageService = new QueueReceiveMessageService(incoming, new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    parseMessage(getObjectFromMsg(message), message.getJMSCorrelationID());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public QueueReceiveGateway(Destinations incoming, String selector) {
        receiveMessageService = new QueueReceiveSpecificMessageService(incoming, selector, new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    parseMessage(getObjectFromMsg(message), message.getJMSCorrelationID());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
