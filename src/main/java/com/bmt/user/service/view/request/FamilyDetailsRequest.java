package com.bmt.user.service.view.request;

import com.bmt.model.user.MaritalStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class FamilyDetailsRequest {
    private UUID userId;
    private String name;
    private String relationship;
    private MaritalStatus maritalStatus;
    private LocalDate dataOfBirth;
    private LocalDate marriageAnniversary;
}
