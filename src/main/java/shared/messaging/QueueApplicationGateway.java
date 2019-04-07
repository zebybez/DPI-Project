package shared.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

//todo make special serializer instead of object messages.
public class QueueApplicationGateway<IN extends Serializable, OUT extends Serializable> extends ApplicationGateway {

    public QueueApplicationGateway(Destinations outgoing, Destinations incoming) {
        messageService = new QueueMessageService(outgoing, incoming, new MessageListener() {
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
