package shared.messaging.receiving;

import com.google.gson.Gson;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

public abstract class ReceiveGateway<IN> {

    public ReceiveMessageService receiveMessageService;

    private Class<IN> type;
    private Gson gson;

    public ReceiveGateway(Class type) {
        this.type = type;
        this.gson = new Gson();
    }

    public IN getObjectFromMsg(Message message) {
        try {
            TextMessage txtMsg = (TextMessage) message;
            return gson.fromJson(txtMsg.getText(), type);
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
