package com.bmt.user.service.controller;

import com.bmt.user.service.service.UserService;
import com.bmt.user.service.view.request.AuthRequest;
import com.bmt.user.service.view.request.ChangePasswordRequest;
import com.bmt.user.service.view.request.OTPRequest;
import com.bmt.user.service.view.request.UserRequest;
import com.bmt.user.service.view.response.AuthResponse;
import com.bmt.user.service.view.response.UserResponse;
import com.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping(value = "/verify-otp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> verifyOTP(@RequestBody @Valid OTPRequest otpRequest) {
        UserResponse response = userService.verifyOTP(otpRequest);
        return ResponseEntity.ok(ApiResponse.success("OTP Verified for User Registration", response));
    }

    @PutMapping(value = "/email/verify-otp")
    public ResponseEntity<ApiResponse<UserResponse>> verifyOtpForEmailUpdate(@RequestBody @Valid OTPRequest otpRequest) {
        UserResponse response = userService.verifyOtpForEmailUpdate(otpRequest);
        return ResponseEntity.ok(ApiResponse.success("OTP Verified for Email Update", response));
    }

    @PutMapping(value = "/mobile/verify-otp")
    public ResponseEntity<ApiResponse<UserResponse>> verifyOtpForMobileUpdate(@RequestBody @Valid OTPRequest otpRequest) {
        UserResponse response = userService.verifyOtpForMobileUpdate(otpRequest);
        return ResponseEntity.ok(ApiResponse.success("OTP Verified for Mobile Update", response));
    }

    @PutMapping(value = "/reset/password/{id}")
    public ResponseEntity<ApiResponse<String>> resetPassword(@PathVariable UUID id, @RequestBody ChangePasswordRequest request) {
        String response = userService.updatePassword(id, request);
        return ResponseEntity.ok(ApiResponse.success("Password Reset", response));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@PathVariable UUID id, @RequestBody UserRequest userRequest) {
        UserResponse response = userService.update(id, userRequest);
        return ResponseEntity.ok(ApiResponse.success("User Updated", response));
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> get(@PathVariable UUID id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("User Detail", response));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAll() {
        List<UserResponse> responseList = userService.getAll();
        return ResponseEntity.ok(ApiResponse.success("User Details", responseList));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> delete(@PathVariable UUID id) {
        UserResponse response = userService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("User Deleted", response));
    }
}
