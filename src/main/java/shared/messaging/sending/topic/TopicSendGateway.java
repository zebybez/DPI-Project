package shared.messaging.sending.topic;

import shared.messaging.Destinations;
import shared.messaging.sending.SendGateway;

public class TopicSendGateway<O> extends SendGateway<O> {
    public TopicSendGateway(Destinations outgoing) {
        sendMessageService = new TopicSendMessageService(outgoing);
    }
}
