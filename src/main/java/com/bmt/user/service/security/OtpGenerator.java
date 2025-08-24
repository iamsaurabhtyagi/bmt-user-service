package com.bmt.user.service.security;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class OtpGenerator {
    private static final SecureRandom secureRandom = new SecureRandom(); // thread-safe
    private static final int OTP_LENGTH = 6;
    private static final int EXPIRY_MINUTES = 5;

    public String generateOtp() {
        int otp = secureRandom.nextInt((int) Math.pow(10, OTP_LENGTH));
        return String.format("%0" + OTP_LENGTH + "d", otp); // ensures leading zeros
    }

    public static LocalDateTime setExpiryTime(int expiryMinutes) {
        return LocalDateTime.now().plus(expiryMinutes, ChronoUnit.MINUTES);
    }

    // Generates both OTP and expiry
    public OtpData generateOtpWithExpiry() {
        String otp = generateOtp();
        LocalDateTime expiryTime = setExpiryTime(EXPIRY_MINUTES);
        return new OtpData(otp, expiryTime);
    }

    public record OtpData(String otp, LocalDateTime expiryTime) {}
}
