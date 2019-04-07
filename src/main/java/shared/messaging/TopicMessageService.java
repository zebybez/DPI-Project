package shared.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

//todo split up in sending and receiving parts
public class TopicMessageService extends MessageService{

    public TopicMessageService(Destinations outgoing, Destinations incoming, MessageListener listener) {
        super();
        try {
            destination = session.createTopic(outgoing.toString());
            recieveDestination = session.createTopic(incoming.toString());

            producer = session.createProducer(destination);
            consumer = session.createConsumer(recieveDestination);
            consumer.setMessageListener(listener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
