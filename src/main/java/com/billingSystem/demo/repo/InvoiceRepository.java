package com.billingSystem.demo.repo;

import com.billingSystem.demo.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,String> {

    //public List<Invoice> findByUser_id(String user_id);

    //public Invoice findBySenderEmail();

    @Query("from Invoice where ReceiverEmail=?1")
    public List<Invoice> findByReceiver_email(String Receiver_email);

    public List<Invoice> findByReceiverEmail(String receiverEmail);

    public List<Invoice>  findBySenderEmail(String senderEmail);

    public Invoice findByInvoiceId(String s);

}
