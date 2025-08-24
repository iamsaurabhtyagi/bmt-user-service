package com.bmt.user.service.service;

import com.bmt.user.service.view.request.AddressRequest;
import com.bmt.user.service.view.request.FamilyDetailsRequest;
import com.bmt.user.service.view.request.PersonalDetailsRequest;
import com.bmt.user.service.view.response.AddressResponse;
import com.bmt.user.service.view.response.FamilyDetailsResponse;
import com.bmt.user.service.view.response.PersonalDetailsResponse;

import java.util.List;

public interface UserProfileService {
    PersonalDetailsResponse updatePersonalDetails(PersonalDetailsRequest personalDetailsRequest);
    AddressResponse updateAddress(AddressRequest addressRequest);
    List<FamilyDetailsResponse> updateFamilyDetails(FamilyDetailsRequest familyDetailsRequest);
}
