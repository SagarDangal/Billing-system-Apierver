package com.billingSystem.demo.controller;


import com.billingSystem.demo.model.authResponse;
import com.billingSystem.demo.model.payload.LoginRequest;
import com.billingSystem.demo.model.payload.RegistrationRequest;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.repo.UserRepository;
import com.billingSystem.demo.services.CustomUserDetailedService;
import com.billingSystem.demo.services.authService;
import com.billingSystem.demo.utils.jwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/")
@EnableWebSecurity
public class userController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    authService authservice;


    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegistrationRequest registrationRequest){

        return authservice.saveuser(registrationRequest);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws AuthenticationException {

        return authservice.login(loginRequest,response);
    }



    @GetMapping("/userlogout")
    public ResponseEntity<?> logout(HttpServletResponse response){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        user.setActive(false);
        userRepository.save(user);
        auth.setAuthenticated(false);
        Cookie cookie = new Cookie("cookie",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new ResponseEntity<>("logout success",HttpStatus.OK);

    }

}
