package shared.domain;

import java.io.Serializable;

public class InvoiceReply implements Serializable {
    private int invoiceNr;
    private String customerEmail;

    public InvoiceReply(int invoiceNr, String customerEmail) {
        this.invoiceNr = invoiceNr;
        this.customerEmail = customerEmail;
    }

    public int getInvoiceNr() {
        return invoiceNr;
    }

    public void setInvoiceNr(int invoiceNr) {
        this.invoiceNr = invoiceNr;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
