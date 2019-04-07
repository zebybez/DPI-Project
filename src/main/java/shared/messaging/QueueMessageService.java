package shared.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

//todo split up in sending and receiving parts
public class QueueMessageService extends MessageService {

    public QueueMessageService(Destinations outgoing, Destinations incoming, MessageListener listener) {
        super();
        try {
            destination = session.createQueue(outgoing.toString());
            recieveDestination = session.createQueue(incoming.toString());

            producer = session.createProducer(destination);
            consumer = session.createConsumer(recieveDestination);
            consumer.setMessageListener(listener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
