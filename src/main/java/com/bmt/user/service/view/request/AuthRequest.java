package com.bmt.user.service.view.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
