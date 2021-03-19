package com.billingSystem.demo.model;

import java.io.Serializable;

public class authResponse implements Serializable {
    private final String jwt;

    public authResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
