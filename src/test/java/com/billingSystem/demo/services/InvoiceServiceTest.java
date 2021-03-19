package com.billingSystem.demo.services;

import com.billingSystem.demo.model.Invoice;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.model.payload.InvoiceRequest;
import com.billingSystem.demo.repo.InvoiceRepository;
import com.billingSystem.demo.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.Before;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InvoiceServiceTest {


    @InjectMocks
    InvoiceService invoiceService;

    @Mock
     UserRepository userRepository;
    @Mock
     InvoiceRepository invoiceRepository;

   @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUsername("sagar@gmail.com");
        user.setFirstName("sagar");
        user.setLastName("Dangal");
        user.setEmail("sagar@gmail.com");
        user.setPassword("123456");
        user.setActive(false);

        //when(userRepository.findByUsernamePassword(loginRequest.getEmail(),loginRequest.getPassword())).thenReturn(user);

        //when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId("123456789");
        invoice.setActive(true);
        invoice.setIssueDate(new Date());
        invoice.setSenderEmail("sagar@sagar.com");
        invoice.setReceiverEmail("test@test.com");
        invoice.setExpireDate(new Date(System.currentTimeMillis() + 30 * 60 * 60 * 24 * 1000L));

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);


       when(invoiceRepository.findByReceiverEmail(invoice.getReceiverEmail())).thenReturn(invoiceList);
       when(invoiceRepository.findBySenderEmail(invoice.getSenderEmail())).thenReturn(invoiceList);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);


    }

    @Test
    void createInvoice() {
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setAmount("1000");
        invoiceRequest.setReceiver_email("test@test.com");
        Invoice invoice = new Invoice();
        invoice.setReceiverEmail(invoiceRequest.getReceiver_email());
        invoice.setAmount(invoiceRequest.getAmount());
        Invoice invoice1 = invoiceService.saveinvoice(invoice);
        assertEquals(invoice1.getReceiverEmail(),invoiceRequest.getReceiver_email());


    }

    @Test
    void getInvoice() {

       String receiver_email ="test@test.com";
        List<Invoice> invoiceList = invoiceRepository.findByReceiverEmail(receiver_email);
        assertEquals(receiver_email,invoiceList.get(0).getReceiverEmail());
    }

    @Test
    void getSentInvoice() {
        String sender_email ="sagar@sagar.com";
        List<Invoice> invoiceList = invoiceRepository.findBySenderEmail(sender_email);
        assertEquals(sender_email,invoiceList.get(0).getSenderEmail());
    }
}