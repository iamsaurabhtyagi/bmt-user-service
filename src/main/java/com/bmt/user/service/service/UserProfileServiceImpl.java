package com.bmt.user.service.service;

import com.bmt.exception.UserNotFoundException;
import com.bmt.model.user.FamilyInformation;
import com.bmt.model.user.PersonalInformation;
import com.bmt.model.user.User;
import com.bmt.model.user.UserAddress;
import com.bmt.user.service.controller.UserProfileController;
import com.bmt.user.service.controller.converter.UserProfileConverter;
import com.bmt.user.service.dao.UserDao;
import com.bmt.user.service.view.request.AddressRequest;
import com.bmt.user.service.view.request.FamilyDetailsRequest;
import com.bmt.user.service.view.request.PersonalDetailsRequest;
import com.bmt.user.service.view.response.AddressResponse;
import com.bmt.user.service.view.response.FamilyDetailsResponse;
import com.bmt.user.service.view.response.PersonalDetailsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserDao userDao;

    public UserProfileServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public PersonalDetailsResponse updatePersonalDetails(PersonalDetailsRequest personalDetailsRequest) {
        User user = userDao.findById(personalDetailsRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Profile: User Not Found"));

        PersonalInformation personalInformation = UserProfileConverter.modelToPersonalInformationEntity(user.getPersonalInformation(), personalDetailsRequest);

        if(user.getPersonalInformation() == null)
            user.setPersonalInformation(personalInformation);
        personalInformation.setUser(user);
        userDao.save(user);

        return UserProfileConverter.entityToPersonalDetailsResponseModel(personalInformation);
    }

    @Override
    public AddressResponse updateAddress(AddressRequest addressRequest) {
        User user = userDao.findById(addressRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Profile: User Not Found"));

        UserAddress userAddress = UserProfileConverter.modelToUserAddressEntity(user.getUserAddress(), addressRequest);

        if(user.getUserAddress() == null)
            user.setUserAddress(userAddress);
        user = userDao.save(user);

        return UserProfileConverter.entityToAddressResponseModel(user.getUserAddress());
    }

    @Override
    public List<FamilyDetailsResponse> updateFamilyDetails(FamilyDetailsRequest familyDetailsRequest) {
        FamilyInformation familyInformation = UserProfileConverter.modelToFamilyInformationEntity(familyDetailsRequest);

        User user = userDao.findById(familyDetailsRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Profile: User Not Found"));

        user.getPersonalInformation().getFamilyInformation().add(familyInformation);
        user = userDao.save(user);

        return UserProfileConverter.entityToFamilyInformationResponseModel(user.getPersonalInformation().getFamilyInformation());
    }
}
