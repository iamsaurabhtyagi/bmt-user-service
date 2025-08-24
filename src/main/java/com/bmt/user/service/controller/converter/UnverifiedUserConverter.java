package com.bmt.user.service.controller.converter;

import com.bmt.model.user.OtpToken;
import com.bmt.model.user.UnverifiedUser;
import com.bmt.model.user.UserLoginUpdateDetails;
import com.bmt.user.service.view.request.OTPRequest;
import com.bmt.user.service.view.request.UserRequest;

import java.util.List;

public class UnverifiedUserConverter {
    public static UnverifiedUser requestToEntityUnverifiedUser(UserRequest request) {
        UnverifiedUser unverifiedUser = new UnverifiedUser();

        unverifiedUser.setUserType(request.getUserType());

        if(request.getMobile() != null)
            unverifiedUser.setPhone(request.getMobile());

        if(request.getEmail() != null)
            unverifiedUser.setEmail(request.getEmail());

        if(request.getPassword() != null)
            unverifiedUser.setPassword(request.getPassword());

        if(request.getPin() != null)
            unverifiedUser.setPin(request.getPin());

        return unverifiedUser;
    }

    public static void entityToResponseUnverifiedUser(UnverifiedUser unverifiedUser) {

    }

    public static UnverifiedUser requestToEntityToResendOtp(OTPRequest otpRequest) {
        UnverifiedUser unverifiedUser = new UnverifiedUser();

        if(otpRequest.getEmail() != null)
            unverifiedUser.setEmail(otpRequest.getEmail());

        if(otpRequest.getPhone() != null)
            unverifiedUser.setPhone(otpRequest.getPhone());

        return unverifiedUser;
    }

    public static UnverifiedUser requestToEntityForUnverifiedUser(OTPRequest otpRequest) {
        UnverifiedUser unverifiedUser = new UnverifiedUser();
        unverifiedUser.setPhone(otpRequest.getPhone());
        unverifiedUser.setEmail(otpRequest.getEmail());

        OtpToken otpToken = new OtpToken();
        otpToken.setOtp(otpRequest.getOtp());
        unverifiedUser.setOtpTokens(List.of(otpToken));

        return unverifiedUser;
    }

    public static UserLoginUpdateDetails requestToEntityForUserLoginUpdateRequest(UserRequest request) {
        UserLoginUpdateDetails userUpdateDetails = new UserLoginUpdateDetails();

        if(request.getEmail() != null)
            userUpdateDetails.setNewEmail(request.getEmail());
        if(request.getMobile() != null)
            userUpdateDetails.setNewPhone(request.getMobile());
        if(request.getUsername() != null)
            userUpdateDetails.setNewUsername(request.getUsername());

        return userUpdateDetails;
    }
}
