package com.bmt.user.service.controller.converter;

import com.bmt.user.service.view.request.OTPRequest;
import com.bmt.user.service.view.request.UserRequest;
import com.bmt.user.service.view.response.UserResponse;
import com.bmt.model.user.*;

import java.util.List;

public class UserConverter {

    public static User requestToEntity(UserRequest request) {
        User user = new User();

        //user.setUserType(UserType.CUSTOMER);
        user.setPhone(request.getMobile());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }

    public static User requestToEntityToUpdateEmail(UserRequest request) {
        User user = new User();

        user.setEmail(request.getEmail());

        return user;
    }

    public static User requestToEntityToUpdateMobile(UserRequest request) {
        User user = new User();

        user.setPhone(request.getMobile());

        return user;
    }

    public static User requestToEntityToResetPassword(User existingUser, UserRequest request) {
        if(existingUser == null)
            existingUser = new User();

        existingUser.setPassword(request.getPassword());

        return existingUser;
    }

    public static UserResponse entityToResponse(User user) {
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUserType(user.getUserType());
        response.setUserStatus(user.getStatus());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());

        return response;
    }
}
