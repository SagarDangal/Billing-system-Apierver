package com.billingSystem.demo.model;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Token {
    private String Token;

    @Id
    private String Userid;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    @Override
    public String toString() {
        return "Token{" +
                "Token='" + Token + '\'' +
                ", Userid='" + Userid + '\'' +
                '}';
    }
}
