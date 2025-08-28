package com.bmt.user.service.view.response;

import com.common.model.user.UserStatus;
import com.common.model.user.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private UserType userType;
    private UserStatus userStatus;
    private String username;
    private String email;
    private String phone;
}
