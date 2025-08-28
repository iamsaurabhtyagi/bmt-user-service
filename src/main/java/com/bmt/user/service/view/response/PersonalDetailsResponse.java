package com.bmt.user.service.view.response;

import com.common.model.user.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonalDetailsResponse {
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
