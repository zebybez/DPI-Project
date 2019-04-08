package shared.messaging.sending;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.Serializable;

public abstract class SendGateway<OUT extends Serializable> {
    public SendMessageService sendMessageService;
    public Message message;

    /***
     * creates a queue to later be sent
     * @param object the object to put in the queue
     */
    public void createMessage(OUT object) {
        try {
            message = sendMessageService.getSession().createObjectMessage(object);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /***
     * sends the queue
     */
    public void sendMessage() {
        try {
            sendMessageService.sendMessage(message);
        } catch (NullPointerException e) {
            System.out.println("instantiate the queue first using the \"createMessage\" Method");
            e.printStackTrace();
        }
    }

    /***
     * sets the correlation id for the next queue sent
     * @param id the id to set
     */
    public void setCorrelationId(String id) {
        try {
            message.setJMSCorrelationID(id);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("instantiate the queue first using the \"createMessage\" Method");
            e.printStackTrace();
        }
    }

    /***
     * gets the queue id of the last queue created
     * @return the id of the queue, or and empty string if it failed
     */
    public String getMessageId() {
        try {
            return message.getJMSMessageID();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("instantiate the queue first using the \"createMessage\" Method");
            e.printStackTrace();
        }
        return "";
    }
}
