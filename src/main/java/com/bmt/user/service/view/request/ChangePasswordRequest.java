package com.bmt.user.service.view.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String emailOrPhone;
    private String oldPassword;
    private String newPassword;
}
