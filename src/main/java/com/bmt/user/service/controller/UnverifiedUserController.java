package com.bmt.user.service.controller;

import com.bmt.user.service.service.UnverifiedUserService;
import com.bmt.user.service.view.request.OTPRequest;
import com.bmt.user.service.view.request.UserRequest;
import com.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
public class UnverifiedUserController {
    private final UnverifiedUserService unverifiedUserService;

    public UnverifiedUserController(UnverifiedUserService unverifiedUserService) {
        this.unverifiedUserService = unverifiedUserService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> register(@RequestBody @Valid UserRequest userRequest) {
        String response = unverifiedUserService.register(userRequest);
        return ResponseEntity.ok(ApiResponse.success("User Registration", response));
    }

    @PostMapping(value = "/resend-otp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> resendOTP(@RequestBody @Valid OTPRequest otpRequest) {
        String response = unverifiedUserService.resendOTP(otpRequest);
        return ResponseEntity.ok(ApiResponse.success("OTP Resent", response));
    }

    @PutMapping(value = "/mobile/{id}")
    public ResponseEntity<ApiResponse<String>> updateMobile(@PathVariable UUID id, @RequestBody UserRequest request) {
        String response = unverifiedUserService.updateUserMobile(id, request);
        return ResponseEntity.ok(ApiResponse.success("Update Mobile", response));
    }

    @PutMapping(value = "/email/{id}")
    public ResponseEntity<ApiResponse<String>> updateEmail(@PathVariable UUID id, @RequestBody UserRequest request) {
        String response = unverifiedUserService.updateUserEmail(id, request);
        return ResponseEntity.ok(ApiResponse.success("Update Email", response));
    }
}
