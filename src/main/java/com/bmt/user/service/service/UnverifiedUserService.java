package com.bmt.user.service.service;

import com.common.model.user.UnverifiedUser;
import com.bmt.user.service.view.request.OTPRequest;
import com.bmt.user.service.view.request.UserRequest;

import java.util.UUID;

public interface UnverifiedUserService {

    /**
     *
     * @param userRequest
     * @return
     */
    String register(UserRequest userRequest);

    /**
     *
     * @param otpRequest
     * @return
     */
    String resendOTP(OTPRequest otpRequest);

    /**
     *
     * @param id
     * @param request
     * @return
     */
    String updateUserMobile(UUID id, UserRequest request);

    /**
     * @param id
     * @param request
     * @return
     */
    String updateUserEmail(UUID id, UserRequest request);

    /**
    *
    * Save or Update method for inter service communication
    */
    UnverifiedUser saveOrUpdate(UnverifiedUser unverifiedUser);
    /**
     *
     * Find Unverified User method for inter service communication
     *
     * */
    UnverifiedUser findUnverifiedUserByEmailOrPhone(String email, String phone);

}
