package com.billingSystem.demo.services;

import com.billingSystem.demo.model.CustomUserDetails;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomUserDetailedServiceTest {

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setup(){
        User user = new User();
        user.setUsername("sagar@gmail.com");
        user.setFirstName("sagar");
        user.setLastName("Dangal");
        user.setEmail("sagar@gmail.com");
        user.setPassword("123456");
        user.setActive(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByEmail(user.getUsername())).thenReturn(user);



    }

    @Test
    void loadUserByUsername() {
        String email="sagar@gmail.com";
        User user = userRepository.findByEmail(email);
        CustomUserDetails customUserDetails = new CustomUserDetails(user.getUsername(),user.getPassword(),new ArrayList<>());
        assertEquals(email,customUserDetails.getUsername());
    }
}