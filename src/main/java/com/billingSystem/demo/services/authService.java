package com.billingSystem.demo.services;

import com.billingSystem.demo.model.Token;
import com.billingSystem.demo.model.User;
import com.billingSystem.demo.model.authResponse;
import com.billingSystem.demo.model.payload.LoginRequest;
import com.billingSystem.demo.model.payload.RegistrationRequest;
import com.billingSystem.demo.repo.TokenRepository;
import com.billingSystem.demo.repo.UserRepository;
import com.billingSystem.demo.utils.jwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class authService {



    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailedService customUserDetailedService;

    @Autowired
    jwtUtils jwtUtils;

    @Autowired
    TokenRepository tokenRepository;

    public User saveregister(User user){
        return userRepository.save(user);
    }

    public Authentication authencateUser(LoginRequest loginRequest) throws Exception{
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            }

    public ResponseEntity<?> saveuser(RegistrationRequest registrationRequest) {

        List<User> list = userRepository.findByEMAIL(registrationRequest.getUser_email());
        if (list.size() != 0) {
            return new ResponseEntity<>("user already exists ", HttpStatus.resolve(403));
        } else {
            User u = new User();
            u.setEmail(registrationRequest.getUser_email());
            u.setPassword(registrationRequest.getUser_password());
            u.setFirstName(registrationRequest.getUser_fname());
            u.setLastName(registrationRequest.getUser_lname());
            u.setUsername(registrationRequest.getUser_email());
            u.setActive(false);
            saveregister(u);
            return new ResponseEntity<>("register successfully", HttpStatus.ACCEPTED);
        }
    }


    public ResponseEntity<?> login(LoginRequest loginRequest, HttpServletResponse response){
        User user = userRepository.findByUsernamePassword(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {

            String Uname = user.getEmail();
            String Upass = user.getPassword();
            if (loginRequest.getEmail().equalsIgnoreCase(Uname) && loginRequest.getPassword().equalsIgnoreCase(Upass)) {
                UsernamePasswordAuthenticationToken authReq
                        = new UsernamePasswordAuthenticationToken(Uname, Upass);
                Authentication auth = authenticationManager.authenticate(authReq);
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(auth);
                final UserDetails userDetails = customUserDetailedService.loadUserByUsername(loginRequest.getEmail());
                final String jwt = jwtUtils.generateToken(userDetails);
                Cookie cookie = new Cookie("cookie",jwt);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(3600);
                response.addCookie(cookie);
                user.setActive(true);
                saveregister(user);
                Token token = new Token();
                token.setToken(jwt);
                token.setUserid(user.getId());
                tokenRepository.save(token);

                return ResponseEntity.ok(new authResponse(jwt));
            }
            else return  new ResponseEntity<>("failed", HttpStatus.UNAUTHORIZED);
        }
        else return new ResponseEntity<>("failed", HttpStatus.UNAUTHORIZED);
    }

   /* public ResponseEntity<String> logout(HttpServletResponse response){
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

    }*/

}
