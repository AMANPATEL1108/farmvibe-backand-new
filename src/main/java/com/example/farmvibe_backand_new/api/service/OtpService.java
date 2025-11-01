package com.example.farmvibe_backand_new.api.service;

public interface OtpService {
    public boolean generateOtp(String phoneNumber);
    public boolean validateOtp(String phoneNumber, String otp);

}
