package com.bmt.user.service.controller.converter;

import com.bmt.model.user.FamilyInformation;
import com.bmt.model.user.PersonalInformation;
import com.bmt.model.user.UserAddress;
import com.bmt.user.service.view.request.AddressRequest;
import com.bmt.user.service.view.request.FamilyDetailsRequest;
import com.bmt.user.service.view.request.PersonalDetailsRequest;
import com.bmt.user.service.view.response.AddressResponse;
import com.bmt.user.service.view.response.FamilyDetailsResponse;
import com.bmt.user.service.view.response.PersonalDetailsResponse;

import java.util.ArrayList;
import java.util.List;

public class UserProfileConverter {
    public static PersonalInformation modelToPersonalInformationEntity(PersonalInformation personalInformation,PersonalDetailsRequest personalDetailsRequest) {

        if(personalInformation == null)
            personalInformation = new PersonalInformation();

        if(personalDetailsRequest.getFirstName() != null)
            personalInformation.setFirstName(personalDetailsRequest.getFirstName());

        if(personalDetailsRequest.getMiddleName() != null)
            personalInformation.setMiddleName(personalDetailsRequest.getMiddleName());

        if(personalDetailsRequest.getLastName() != null)
            personalInformation.setLastName(personalDetailsRequest.getLastName());

        personalInformation.setFullName(personalInformation.getFirstName() + " "
                + personalInformation.getMiddleName() + " "
                + personalInformation.getLastName());

        if(personalDetailsRequest.getGender() != null)
            personalInformation.setGender(personalDetailsRequest.getGender());

        if(personalDetailsRequest.getMaritalStatus() != null)
            personalInformation.setMaritalStatus(personalDetailsRequest.getMaritalStatus());

        if(personalDetailsRequest.getDataOfBirth() != null)
            personalInformation.setDataOfBirth(personalDetailsRequest.getDataOfBirth());

        if(personalDetailsRequest.getMarriageAnniversary() != null)
            personalInformation.setMarriageAnniversary(personalDetailsRequest.getMarriageAnniversary());

        return personalInformation;
    }

    public static PersonalDetailsResponse entityToPersonalDetailsResponseModel(PersonalInformation personalInformation) {
        PersonalDetailsResponse response = new PersonalDetailsResponse();

        response.setFirstName(personalInformation.getFirstName());
        response.setMiddleName(personalInformation.getMiddleName());
        response.setLastName(personalInformation.getLastName());
        response.setFullName(personalInformation.getFullName());
        response.setGender(personalInformation.getGender());
        response.setMaritalStatus(personalInformation.getMaritalStatus());
        response.setProfilePicUrl(personalInformation.getProfilePicUrl());
        response.setDataOfBirth(personalInformation.getDataOfBirth());
        response.setMarriageAnniversary(personalInformation.getMarriageAnniversary());

        return response;
    }

    public static UserAddress modelToUserAddressEntity(UserAddress userAddress, AddressRequest addressRequest) {
        if(userAddress == null)
            userAddress = new UserAddress();

        if(addressRequest.getAddressLine1() != null)
            userAddress.setAddressLine1(addressRequest.getAddressLine1());

        if(addressRequest.getAddressLine2() != null)
            userAddress.setAddressLine2(addressRequest.getAddressLine2());

        if(addressRequest.getCity() != null)
            userAddress.setCity(addressRequest.getCity());

        if(addressRequest.getState() != null)
            userAddress.setState(addressRequest.getState());

        if(addressRequest.getPostalCode() != null)
            userAddress.setPostalCode(addressRequest.getPostalCode());

        if(addressRequest.getLatitude() != null)
            userAddress.setLatitude(addressRequest.getLatitude());

        if(addressRequest.getLongitude() != null)
            userAddress.setLongitude(addressRequest.getLongitude());

        return userAddress;
    }

    public static AddressResponse entityToAddressResponseModel(UserAddress userAddress) {
        AddressResponse response = new AddressResponse();

        response.setAddressLine1(userAddress.getAddressLine1());
        response.setAddressLine2(userAddress.getAddressLine2());
        response.setCity(userAddress.getCity());
        response.setState(userAddress.getState());
        response.setPostalCode(userAddress.getPostalCode());
        response.setLatitude(userAddress.getLatitude());
        response.setLongitude(userAddress.getLongitude());

        return response;
    }

    public static FamilyInformation modelToFamilyInformationEntity(FamilyDetailsRequest familyDetailsRequest) {
        FamilyInformation familyInformation = new FamilyInformation();

        familyInformation.setName(familyDetailsRequest.getName());
        familyInformation.setRelationship(familyDetailsRequest.getRelationship());
        familyInformation.setDataOfBirth(familyDetailsRequest.getDataOfBirth());
        familyInformation.setMaritalStatus(familyDetailsRequest.getMaritalStatus());
        familyInformation.setMarriageAnniversary(familyDetailsRequest.getMarriageAnniversary());

        return familyInformation;
    }

    public static List<FamilyDetailsResponse> entityToFamilyInformationResponseModel(List<FamilyInformation> familyInformation) {
        List<FamilyDetailsResponse> responses = new ArrayList<>();

        familyInformation.stream().forEach(fi -> responses.add(prepareFamilyDetailsResponse(fi)));

        return responses;
    }

    public static FamilyDetailsResponse prepareFamilyDetailsResponse(FamilyInformation fi) {
        FamilyDetailsResponse response = new FamilyDetailsResponse();

        response.setName(fi.getName());
        response.setMaritalStatus(fi.getMaritalStatus());
        response.setRelationship(fi.getRelationship());
        response.setDataOfBirth(fi.getDataOfBirth());
        response.setMarriageAnniversary(fi.getMarriageAnniversary());

        return response;
    }
}
