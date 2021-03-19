package com.billingSystem.demo.repo;

import com.billingSystem.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    public Transaction findById(int id);
    public Transaction findByInvoiceId(String s);
    public List<Transaction> findBySenderEmail(String senderEmail);
    public List<Transaction> findByReceiverEmail(String receiverEmail);
}
