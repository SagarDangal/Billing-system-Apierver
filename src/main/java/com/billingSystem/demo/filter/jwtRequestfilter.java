package com.billingSystem.demo.filter;

import com.billingSystem.demo.model.Token;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.repo.TokenRepository;
import com.billingSystem.demo.repo.UserRepository;
import com.billingSystem.demo.services.CustomUserDetailedService;
import com.billingSystem.demo.utils.jwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


@Component
@Slf4j
public class jwtRequestfilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailedService userDetailsService;

    @Autowired
    private jwtUtils jwtUtil;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException,NullPointerException {

        //final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        Cookie[] cookie = request.getCookies();
        try {


            String authorizationHeader = Arrays.stream(cookie).map(c -> c.getName() + "=" + c.getValue())
                    .collect(Collectors.joining(", "));

            
            if (authorizationHeader != null && authorizationHeader.startsWith("cookie")) {


                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);

            }

        }catch (Exception e){
            e.getMessage();

        }


        try {

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }catch (Exception e){
            e.getMessage();
        }
        chain.doFilter(request, response);
    }

}
