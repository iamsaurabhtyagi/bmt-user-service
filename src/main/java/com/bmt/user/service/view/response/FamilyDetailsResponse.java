package com.bmt.user.service.view.response;

import com.bmt.model.user.MaritalStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FamilyDetailsResponse {
    private String name;
    private String relationship;
    private MaritalStatus maritalStatus;
    private LocalDate dataOfBirth;
    private LocalDate marriageAnniversary;
}
