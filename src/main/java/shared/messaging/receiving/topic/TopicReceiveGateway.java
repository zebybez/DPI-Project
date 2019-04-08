package shared.messaging.receiving.topic;

import shared.messaging.Destinations;
import shared.messaging.receiving.ReceiveGateway;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.Serializable;

//todo make special serializer instead of object messages.
public class TopicReceiveGateway<O extends Serializable> extends ReceiveGateway<O> {

    public TopicReceiveGateway(Destinations incoming, String clientId, String subscriptionName) {
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
