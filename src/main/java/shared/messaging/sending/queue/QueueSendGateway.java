package shared.messaging.sending.queue;

import shared.messaging.Destinations;
import shared.messaging.sending.SendGateway;

import java.io.Serializable;

public class QueueSendGateway<O extends Serializable> extends SendGateway<O> {
    public QueueSendGateway(Destinations outgoing) {
        sendMessageService = new QueueSendMessageService(outgoing);
    }
}
