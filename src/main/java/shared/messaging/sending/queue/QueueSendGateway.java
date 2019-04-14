package shared.messaging.sending.queue;

import shared.messaging.Destinations;
import shared.messaging.sending.SendGateway;

public class QueueSendGateway<O> extends SendGateway<O> {
    public QueueSendGateway(Destinations outgoing) {
        sendMessageService = new QueueSendMessageService(outgoing);
    }
}
