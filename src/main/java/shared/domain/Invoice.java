package shared.domain;

import java.util.Date;

public class Invoice {
    private String customerEmail;
    private String EventId;
    private int invoiceNr;
    private double price;
    private Date date;

    public Invoice(String customerEmail, String eventId, int invoiceNr, double price, Date date) {
        this.customerEmail = customerEmail;
        EventId = eventId;
        this.invoiceNr = invoiceNr;
        this.price = price;
        this.date = date;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getEventId() {
        return EventId;
    }

    public int getInvoiceNr() {
        return invoiceNr;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

}
