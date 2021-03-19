package com.billingSystem.demo.model.payload;

import java.sql.Date;

public class InvoiceRequest {




    private String receiver_email;

    private String amount;

    public String getReceiver_email() {
        return receiver_email;
    }

    public void setReceiver_email(String receiver_email) {
        this.receiver_email = receiver_email;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "InvoiceRequest{" +
                "receiver_email='" + receiver_email + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
