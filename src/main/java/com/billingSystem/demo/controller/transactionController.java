package com.billingSystem.demo.controller;

import com.billingSystem.demo.model.Invoice;
import com.billingSystem.demo.model.Transaction;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.model.dto.paymentDto;
import com.billingSystem.demo.model.payload.TransactionRequest;
import com.billingSystem.demo.repo.InvoiceRepository;
import com.billingSystem.demo.repo.TransactionRepository;
import com.billingSystem.demo.repo.UserRepository;
import com.billingSystem.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class transactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/payInvoice")
    public ResponseEntity<?> payInvoice(@RequestBody TransactionRequest transactionRequest){
        return transactionService.payInvoice(transactionRequest);
    }

    @GetMapping("/transactionHistory")
    public ResponseEntity<?> transactionHistory(){
        return transactionService.transactionHistory();
    }
}
