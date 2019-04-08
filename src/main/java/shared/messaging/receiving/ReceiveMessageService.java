package shared.messaging.receiving;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public abstract class ReceiveMessageService {

    public Connection connection; // to connect to the ActiveMQ
    public Session session; // session for creating messages, producers and

    public Destination recievedestination; //reference to a queue/topic

    public MessageConsumer consumer; // for receiving messages
    public MessageListener listener; // listens for messages

    public ReceiveMessageService() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        try {
            connection = connectionFactory.createConnection();

            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e){
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public MessageListener getListener() {
        return listener;
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
        try {
            consumer.setMessageListener(listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
