package com.billingSystem.demo.controller;

import com.billingSystem.demo.model.CustomUserDetails;
import com.billingSystem.demo.model.Invoice;
import com.billingSystem.demo.model.payload.InvoiceRequest;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.repo.InvoiceRepository;
import com.billingSystem.demo.repo.UserRepository;
import com.billingSystem.demo.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/invoice")
public class invoiceController {

   @Autowired
    InvoiceService invoiceService;

    @PostMapping("/create_invoice")
    public ResponseEntity<?> create_invoice(@RequestBody InvoiceRequest invoiceRequest){
       return invoiceService.createInvoice(invoiceRequest);

    }
    @GetMapping("/received_invoices")
    public ResponseEntity<?> receive_invoice(HttpServletRequest request){
       return invoiceService.getInvoice();
    }

    @GetMapping("/sent_invoices")
    public ResponseEntity<?> sent_invoice(){
        return invoiceService.getSentInvoice();
    }

}
