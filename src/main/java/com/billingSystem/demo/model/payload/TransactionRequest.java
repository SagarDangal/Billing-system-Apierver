package com.billingSystem.demo.model.payload;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private String InvoiceId;

    private Integer srcAccountNumber;

    private Integer distAccountNUmber;

    private String amount;

    public String getInvoiceId() {
        return InvoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        InvoiceId = invoiceId;
    }

    public Integer getSrcAccountNumber() {
        return srcAccountNumber;
    }

    public void setSrcAccountNumber(Integer srcAccountNumber) {
        this.srcAccountNumber = srcAccountNumber;
    }

    public Integer getDistAccountNUmber() {
        return distAccountNUmber;
    }

    public void setDistAccountNUmber(Integer distAccountNUmber) {
        this.distAccountNUmber = distAccountNUmber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
