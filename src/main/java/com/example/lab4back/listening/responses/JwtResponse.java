package com.example.lab4back.listening.responses;

public class JwtResponse {

    private String token;
    private final String type = "Bearer";
    private long id;
    private String username;

    public JwtResponse() {
        this.token = null;
        this.id = -1;
        this.username = null;
    }

    public JwtResponse(String token, long id, String username) {
        this.token = token;
        this.id = id;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
