package client;

import shared.domain.Event;

public class ClientParser {
    public void parseEvent(Event event, String correlationId) {
        System.out.println("received: " + event.info());
    }
}
