package shared.domain;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable {
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

    public String info(){
        StringBuilder builder = new StringBuilder();
        builder.append("Invoice with Nr: ")
                .append(invoiceNr)
                .append("| for customer:")
                .append(customerEmail)
                .append("| Price: ")
                .append(price)
                .append("| For event ")
                .append(EventId)
                .append("| Date: ")
                .append(date);
        return builder.toString();
    }
}
