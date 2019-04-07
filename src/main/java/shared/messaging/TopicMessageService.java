package shared.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

//todo split up in sending and receiving parts
public class TopicMessageService extends MessageService{

    public TopicMessageService(Destinations outgoing, Destinations incoming, String clientId, String subscriptionName, MessageListener listener) {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        try {

            connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic(outgoing.toString());
            recieveDestination = session.createTopic(incoming.toString());


            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            consumer = session.createDurableSubscriber((Topic)recieveDestination, subscriptionName);
            consumer.setMessageListener(listener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
