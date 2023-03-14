package com.example.lab4back.listening.requests;
import jakarta.validation.constraints.*;

public class AppendingRequest {
    @NotNull
    @Max(2)
    @Min(-2)
    private Double x;
    @NotNull
    @Max(5)
    @Min(-5)
    private Double y;
    @NotNull
    @Max(2)
    @Min(-2)
    private Double r;
    @NotBlank
    private String token;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
