package com.example.farmvibe_backand_new.api.dto;


import lombok.Data;

@Data
public class OtpVerificationRequest {
    private String username;
    private String otp;
}
