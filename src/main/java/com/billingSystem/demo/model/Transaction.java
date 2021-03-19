package com.billingSystem.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Transaction {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String transaction_id;


    @Column(name = "INVOICEID", unique = true)
    private String invoiceId;

    @NaturalId
    @Column(name = "SENDEREMAIL")
    private String senderEmail;


    @Column(name = "RECEUVEREMAIL")
    private String receiverEmail;

    private Integer srcAccountNumber;

    private Integer distAccountNumber;

    private String amount;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String id) {
        this.transaction_id = transaction_id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public Integer getSrcAccountNumber() {
        return srcAccountNumber;
    }

    public void setSrcAccountNumber(Integer srcAccountNumber) {
        this.srcAccountNumber = srcAccountNumber;
    }

    public Integer getDistAccountNumber() {
        return distAccountNumber;
    }

    public void setDistAccountNumber(Integer distAccountNumber) {
        this.distAccountNumber = distAccountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
