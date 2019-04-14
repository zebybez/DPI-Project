package shared.messaging.receiving.topic;

import shared.messaging.Destinations;
import shared.messaging.receiving.ReceiveGateway;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

//todo make special serializer instead of object messages.
public class TopicReceiveGateway<O> extends ReceiveGateway<O> {

    public TopicReceiveGateway(Class type, Destinations incoming, String clientId, String subscriptionName) {
        super(type);
        receiveMessageService = new TopicReceiveMessageService(incoming, clientId, subscriptionName, new MessageListener() {
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
