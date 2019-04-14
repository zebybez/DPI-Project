package shared.messaging.receiving.queue;

import shared.messaging.Destinations;
import shared.messaging.receiving.ReceiveGateway;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

//todo make special serializer instead of object messages.
public class QueueReceiveGateway<O> extends ReceiveGateway<O> {

    public QueueReceiveGateway(Class type, Destinations incoming) {
        super(type);
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

    public QueueReceiveGateway(Class type, Destinations incoming, String selector) {
        super(type);
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
