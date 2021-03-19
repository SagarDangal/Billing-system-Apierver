package com.billingSystem.demo.services;

import com.billingSystem.demo.model.Transaction;
import com.billingSystem.demo.model.payload.InvoiceRequest;
import com.billingSystem.demo.model.payload.TransactionRequest;
import com.billingSystem.demo.repo.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        Transaction transaction = new Transaction();
        transaction.setDistAccountNumber(12345678);
        transaction.setSrcAccountNumber(87654321);
        transaction.setReceiverEmail("receiver@receiver.com");
        transaction.setAmount("500");
        transaction.setSenderEmail("test@test.com");

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Mockito.when(transactionRepository.findByReceiverEmail(transaction.getReceiverEmail())).thenReturn(transactions);
        Mockito.when(transactionRepository.findBySenderEmail(transaction.getSenderEmail())).thenReturn(transactions);

    }

    @Test
    void payInvoice() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSrcAccountNumber(87654321);
        transactionRequest.setDistAccountNUmber(12345678);
        transactionRequest.setAmount("500");

        Transaction transaction = new Transaction();
        transaction.setSenderEmail(transactionRequest.getAmount());
        transaction.setSrcAccountNumber(transactionRequest.getSrcAccountNumber());
        transaction.setDistAccountNumber(transactionRequest.getDistAccountNUmber());
        transaction.setSenderEmail("sender@sender.com");
        transaction.setReceiverEmail("receiver@receiver.com");

        Transaction transaction1 = transactionService.savetransaction(transaction);
        assertEquals(transaction.getReceiverEmail(),transaction1.getReceiverEmail());

    }

    @Test
    void transactionHistory() {

        String email ="receiver@receiver.com";
        List<Transaction> transactions = transactionRepository.findByReceiverEmail(email);
        transactions.addAll(transactionRepository.findBySenderEmail(email));

        assertEquals(email,transactions.get(0).getReceiverEmail());


    }
}