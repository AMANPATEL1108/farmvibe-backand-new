package com.example.farmvibe_backand_new.api.dto;

public class TokenVerificationRequest {
    private String token;

    public TokenVerificationRequest() {}

    public TokenVerificationRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
