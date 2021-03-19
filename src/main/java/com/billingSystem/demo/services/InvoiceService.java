package com.billingSystem.demo.services;


import com.billingSystem.demo.model.Invoice;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.model.payload.InvoiceRequest;
import com.billingSystem.demo.repo.InvoiceRepository;
import com.billingSystem.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    UserRepository userRepository;

    public Invoice saveinvoice(Invoice invoice){
        return invoiceRepository.save(invoice);
    }

    public ResponseEntity<String> createInvoice(InvoiceRequest invoiceRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        if(user.getEmail().equals(invoiceRequest.getReceiver_email())){
            return new ResponseEntity<>("please send invoice to different email ",HttpStatus.BAD_GATEWAY);
        }
        Invoice invoice = new Invoice();
        invoice.setReceiverEmail(invoiceRequest.getReceiver_email());
        invoice.setSenderEmail(user.getEmail());
        invoice.setAmount(invoiceRequest.getAmount());
        invoice.setIssueDate(new Date());
        invoice.setActive(true);
        invoice.setExpireDate(new Date(System.currentTimeMillis()+30*24*60*60*1000L));
        saveinvoice(invoice);
        return ResponseEntity.ok("sucess");
    }

    public ResponseEntity<?> getInvoice(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        List<Invoice> invoices = invoiceRepository.findByReceiverEmail(user.getEmail());
        for(int i=0;i<invoices.size();i++){
            Invoice invoice = invoices.get(i);
            if (invoice.getExpireDate().before(new Date())){
                invoice.setIssueDate(invoice.getExpireDate());
                invoice.setActive(true);
                saveinvoice(invoice);
            }
        }
        return ResponseEntity.ok(invoiceRepository.findByReceiver_email(user.getEmail()));

    }

    public ResponseEntity<?> getSentInvoice(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        List<Invoice> invoices = invoiceRepository.findBySenderEmail(user.getEmail());
        for(int i=0;i<invoices.size();i++){
            Invoice invoice = invoices.get(i);
            if (invoice.getExpireDate().before(new Date())){
                invoice.setIssueDate(invoice.getExpireDate());
                invoice.setActive(true);
                saveinvoice(invoice);
            }
        }
        return ResponseEntity.ok(invoiceRepository.findBySenderEmail(user.getEmail()));

    }





}
