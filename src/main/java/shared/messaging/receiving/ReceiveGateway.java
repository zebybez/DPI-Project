package shared.messaging.receiving;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.io.Serializable;

public abstract class ReceiveGateway<IN extends Serializable> {

    public ReceiveMessageService receiveMessageService;

    public IN getObjectFromMsg(Message message) {
        ObjectMessage objMsg = (ObjectMessage) message;
        try {
            IN object = (IN) objMsg.getObject();
            return object;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * exposes the object gotten from an incoming queue to the parent class;
     * @param object the object in the queue
     */
    public void parseMessage(IN object, String correlationId) {
        throw new IllegalStateException("this method should be overridden");
    }
}
