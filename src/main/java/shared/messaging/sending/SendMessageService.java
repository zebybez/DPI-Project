package shared.messaging.sending;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public abstract class SendMessageService {

    public Connection connection; // to connect to the ActiveMQ
    public Session session; // session for creating messages, producers and

    public Destination destination; // reference to a queue/topic destination

    public MessageProducer producer; // for sending messages

    public SendMessageService() {
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

    public boolean sendMessage(Message objMsg) {
        try {
            String test = objMsg.getJMSMessageID();
            producer.send(objMsg);
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
            return false;
        }
    }
}
