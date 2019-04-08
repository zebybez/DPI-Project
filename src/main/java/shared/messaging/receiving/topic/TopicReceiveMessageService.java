package shared.messaging.receiving.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import shared.messaging.Destinations;
import shared.messaging.receiving.ReceiveMessageService;

import javax.jms.*;

public class TopicReceiveMessageService extends ReceiveMessageService {

    public TopicReceiveMessageService(Destinations incoming, String clientId, String subscriptionName, MessageListener listener) {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            recievedestination = session.createTopic(incoming.toString());

            consumer = session.createDurableSubscriber((Topic) recievedestination, subscriptionName);
            consumer.setMessageListener(listener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
