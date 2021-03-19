package com.billingSystem.demo.services;


import com.billingSystem.demo.model.Invoice;
import com.billingSystem.demo.model.Transaction;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.model.dto.paymentDto;
import com.billingSystem.demo.model.payload.TransactionRequest;
import com.billingSystem.demo.repo.InvoiceRepository;
import com.billingSystem.demo.repo.TransactionRepository;
import com.billingSystem.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TransactionService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction savetransaction(Transaction transaction){
        return transactionRepository.save(transaction);

    }

    public ResponseEntity<?> payInvoice( TransactionRequest transactionRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        Invoice invoice = invoiceRepository.findByInvoiceId(transactionRequest.getInvoiceId());
        paymentDto paymentDto = new paymentDto();
        boolean paymnet=  paymentDto.paymentDto(transactionRequest.getSrcAccountNumber(),transactionRequest.getDistAccountNUmber(),transactionRequest.getAmount());

        if (invoice != null && invoice.getActive() && paymnet && user.getEmail().equals(invoice.getReceiverEmail()) && invoice.getAmount().equals(transactionRequest.getAmount())) {
            Transaction transaction = new Transaction();
            transaction.setAmount(transactionRequest.getAmount());
            transaction.setSenderEmail(invoice.getReceiverEmail());
            transaction.setInvoiceId(transactionRequest.getInvoiceId());
            transaction.setReceiverEmail(invoice.getSenderEmail());
            transaction.setSrcAccountNumber(transactionRequest.getSrcAccountNumber());
            transaction.setDistAccountNumber(transactionRequest.getDistAccountNUmber());
            savetransaction(transaction);
            transactionRepository.flush();
            invoice.setActive(false);
            invoiceRepository.save(invoice);
            return ResponseEntity.ok("save");
        }
        return  new ResponseEntity<>("failed Transaction", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> transactionHistory(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        List<Transaction> transactions = transactionRepository.findBySenderEmail(user.getEmail());
        transactions.addAll(transactionRepository.findByReceiverEmail(user.getEmail()));
        return ResponseEntity.ok(transactions);
    }


}
