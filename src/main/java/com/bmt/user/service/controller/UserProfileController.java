package com.bmt.user.service.controller;

import com.bmt.user.service.service.UserProfileService;
import com.bmt.user.service.view.request.AddressRequest;
import com.bmt.user.service.view.request.FamilyDetailsRequest;
import com.bmt.user.service.view.request.PersonalDetailsRequest;
import com.bmt.user.service.view.response.AddressResponse;
import com.bmt.user.service.view.response.FamilyDetailsResponse;
import com.bmt.user.service.view.response.PersonalDetailsResponse;
import com.common.response.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PutMapping(value = "/personal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<PersonalDetailsResponse>> updatePersonal(@RequestBody PersonalDetailsRequest personalDetailsRequest) {
        PersonalDetailsResponse response = userProfileService.updatePersonalDetails(personalDetailsRequest);
        return ResponseEntity.ok(ApiResponse.success("Personal Details updated successfully", response));
    }

    @GetMapping(value = "/personal/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<PersonalDetailsResponse>> getPersonal(@PathVariable UUID userId) {
        PersonalDetailsResponse response = userProfileService.getPersonalDetails(userId);
        return ResponseEntity.ok(ApiResponse.success("Personal Details updated successfully", response));
    }

    @PutMapping(value = "/address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(@RequestBody AddressRequest addressRequest) {
        AddressResponse response = userProfileService.updateAddress(addressRequest);
        return ResponseEntity.ok(ApiResponse.success("Address updated successfully", response));
    }

    @GetMapping(value = "/address/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AddressResponse>> getUserAddress(@PathVariable UUID userId) {
        AddressResponse response = userProfileService.getAddress(userId);
        return ResponseEntity.ok(ApiResponse.success("Address updated successfully", response));
    }

    @PutMapping(value = "/family", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<FamilyDetailsResponse>>> updateFamily(@RequestBody FamilyDetailsRequest familyDetailsRequest) {
        List<FamilyDetailsResponse> response = userProfileService.updateFamilyDetails(familyDetailsRequest);
        return ResponseEntity.ok(ApiResponse.success("Family Details updated successfully", response));
    }

    @GetMapping(value = "/family/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<FamilyDetailsResponse>>> getUserFamily(@PathVariable UUID userId) {
        List<FamilyDetailsResponse> response = userProfileService.getFamilyDetails(userId);
        return ResponseEntity.ok(ApiResponse.success("Family Details updated successfully", response));
    }
}
