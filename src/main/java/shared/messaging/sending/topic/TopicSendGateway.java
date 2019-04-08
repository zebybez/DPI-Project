package shared.messaging.sending.topic;

import shared.messaging.Destinations;
import shared.messaging.sending.SendGateway;

import java.io.Serializable;

public class TopicSendGateway<O extends Serializable> extends SendGateway<O> {
    public TopicSendGateway(Destinations outgoing) {
        sendMessageService = new TopicSendMessageService(outgoing);
    }
}
