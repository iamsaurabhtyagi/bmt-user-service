package com.bmt.user.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
@Table(name = "bmt_customer_user")
public class CustomerUser {
    @Id
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String userType;
    private String username;
    private String email;
    private String password;
    private int mobile;
    private String profilePicUrl;
    private String status;
    private Date created;
    private Date updated;
}
