package shared.messaging;

public enum Destinations {

    EVENT("event.topic"),
    NEW_EVENT("newEvent.queue"),
    ATTEND_EVENT("attendEvent.queue"),

    INVOICE("invoice.queue"),
    INVOICE_REPLY("invoiceReply.queue"),

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
