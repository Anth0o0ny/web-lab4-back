package com.example.lab4back.listening.requests;

import jakarta.validation.constraints.NotBlank;

public class GettingRequest {

    @NotBlank
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
