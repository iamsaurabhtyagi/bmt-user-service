package com.bmt.user.service.service;

import com.bmt.user.service.view.request.AuthRequest;
import com.bmt.user.service.view.request.ChangePasswordRequest;
import com.bmt.user.service.view.request.OTPRequest;
import com.bmt.user.service.view.request.UserRequest;
import com.bmt.user.service.view.response.AuthResponse;
import com.bmt.user.service.view.response.UserResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface UserService {

    //AuthResponse authenticate(AuthRequest request);

    UserResponse verifyOTP(OTPRequest otpRequest);

    UserResponse verifyOtpForEmailUpdate(OTPRequest otpRequest);

    UserResponse update(UUID id, UserRequest userRequest);

    String updatePassword(UUID id, ChangePasswordRequest request);

    UserResponse verifyOtpForMobileUpdate(OTPRequest otpRequest);

    String updateProfilePic();

    UserResponse delete(UUID id);

    UserResponse getUserById(UUID id);

    UserResponse getUserByEmailOrPhoneOrUsername(UserRequest userRequest);

    List<UserResponse> getAll();

    List<UserResponse> getAllByStatus(UserRequest userRequest);

    List<UserResponse> getAllByCreatedAtDate(Date startDate, Date endDate);
}
