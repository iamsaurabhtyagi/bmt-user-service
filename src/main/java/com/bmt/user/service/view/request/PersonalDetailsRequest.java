package com.bmt.user.service.view.request;

import com.common.model.user.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PersonalDetailsRequest {
    private UUID userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private Gender gender;
    private String maritalStatus;
    private String profilePicUrl;
    private LocalDate dataOfBirth;
    private LocalDate marriageAnniversary;
}
