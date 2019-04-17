package shared.messaging;

public enum Destinations {

    EVENT_TOPIC("event.topic"),
    EVENT("newEvent.queue"),
    ATTEND_EVENT("attendEvent.queue"),

    INVOICE("invoice.queue"),
    INVOICE_REPLY("invoiceReply.queue"),

    DEAD_LETTER("deadletter.queue"),

    NO_DESTINATION("invalid");


    private String destination;

    Destinations(String destination){
        this.destination = destination;
    }

    @Override
    public String toString() {
        return destination;
    }
}
