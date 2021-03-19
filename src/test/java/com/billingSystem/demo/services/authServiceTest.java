package com.billingSystem.demo.services;

import com.billingSystem.demo.DemoApplication;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.model.payload.LoginRequest;
import com.billingSystem.demo.model.payload.RegistrationRequest;
import com.billingSystem.demo.repo.TokenRepository;
import com.billingSystem.demo.repo.UserRepository;
import com.billingSystem.demo.utils.jwtUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class authServiceTest {

    @InjectMocks
    authService authService;

    @Mock
    UserRepository userRepository;

    @Mock
    CustomUserDetailedService customUserDetailedService;


    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    com.billingSystem.demo.utils.jwtUtils jwtUtils;

    @Mock
    TokenRepository tokenRepository;


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
        when(userRepository.findByUsernamePassword(user.getUsername(),user.getPassword())).thenReturn(user);



    }


        @Test
    void authencateUser() {
    }

    @Test
    void saveuser() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUser_email("sagar@gmail.com");
        registrationRequest.setUser_fname("sagar");
        registrationRequest.setUser_lname("Dangal");
        registrationRequest.setUser_password("123456");

        User user = new User();
        user.setUsername(registrationRequest.getUser_email());
        user.setFirstName(registrationRequest.getUser_fname());
        user.setLastName(registrationRequest.getUser_lname());
        user.setEmail(registrationRequest.getUser_email());
        user.setPassword(registrationRequest.getUser_password());
        user.setActive(false);
        //when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser=authService.saveregister(user);
        assertEquals(savedUser.getEmail(),user.getEmail());


    }

    @Test
    void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("sagar@gmail.com");
        loginRequest.setPassword("123456");
        //when(userRepository.findByUsernamePassword(loginRequest.getEmail(),loginRequest.getPassword())).thenReturn();
        User user =userRepository.findByUsernamePassword(loginRequest.getEmail(),loginRequest.getPassword());

        assertEquals(loginRequest.getEmail(),user.getEmail());


    }

    @Test
    void logout() {
    }
}