package com.bmt.user.service.service;

import com.bmt.user.service.view.request.AddressRequest;
import com.bmt.user.service.view.request.FamilyDetailsRequest;
import com.bmt.user.service.view.request.PersonalDetailsRequest;
import com.bmt.user.service.view.response.AddressResponse;
import com.bmt.user.service.view.response.FamilyDetailsResponse;
import com.bmt.user.service.view.response.PersonalDetailsResponse;

import java.util.List;
import java.util.UUID;

public interface UserProfileService {
    PersonalDetailsResponse updatePersonalDetails(PersonalDetailsRequest personalDetailsRequest);
    PersonalDetailsResponse getPersonalDetails(UUID userId);
    AddressResponse updateAddress(AddressRequest addressRequest);
    AddressResponse getAddress(UUID userId);
    List<FamilyDetailsResponse> updateFamilyDetails(FamilyDetailsRequest familyDetailsRequest);
    List<FamilyDetailsResponse> getFamilyDetails(UUID userId);
}
