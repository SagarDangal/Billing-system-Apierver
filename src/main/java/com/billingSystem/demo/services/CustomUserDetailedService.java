package com.billingSystem.demo.services;

import com.billingSystem.demo.model.CustomUserDetails;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailedService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailedService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User dbUser = userRepository.findByEmail(email);
        CustomUserDetails customUserDetails = new CustomUserDetails(dbUser.getUsername(),dbUser.getPassword(),new ArrayList<>());
        return customUserDetails;
           }



}
