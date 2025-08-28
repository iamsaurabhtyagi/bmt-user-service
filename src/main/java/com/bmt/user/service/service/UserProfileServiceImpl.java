package com.bmt.user.service.service;

import com.bmt.exception.NoUserInformationException;
import com.bmt.exception.UserNotFoundException;
import com.common.model.user.FamilyInformation;
import com.common.model.user.PersonalInformation;
import com.common.model.user.User;
import com.common.model.user.UserAddress;
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
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserDao userDao;

    public UserProfileServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public PersonalDetailsResponse updatePersonalDetails(PersonalDetailsRequest personalDetailsRequest) {
        User user = getUser(personalDetailsRequest.getUserId());

        PersonalInformation personalInformation = UserProfileConverter.modelToPersonalInformationEntity(user.getPersonalInformation(), personalDetailsRequest);

        if(user.getPersonalInformation() == null)
            user.setPersonalInformation(personalInformation);
        personalInformation.setUser(user);
        userDao.save(user);

        return UserProfileConverter.entityToPersonalDetailsResponseModel(personalInformation);
    }

    @Override
    public PersonalDetailsResponse getPersonalDetails(UUID userId) {
        User user = getUser(userId);

        PersonalInformation personalInformation = user.getPersonalInformation();
        if(personalInformation == null)
            throw new NoUserInformationException("No Personal Information");

        return UserProfileConverter.entityToPersonalDetailsResponseModel(personalInformation);
    }

    @Override
    public AddressResponse updateAddress(AddressRequest addressRequest) {
        User user = getUser(addressRequest.getUserId());

        UserAddress userAddress = UserProfileConverter.modelToUserAddressEntity(user.getUserAddress(), addressRequest);

        if(user.getUserAddress() == null)
            user.setUserAddress(userAddress);
        userAddress.setUser(user);
        user = userDao.save(user);

        return UserProfileConverter.entityToAddressResponseModel(user.getUserAddress());
    }

    @Override
    public AddressResponse getAddress(UUID userId) {
        User user = getUser(userId);

        UserAddress userAddress = user.getUserAddress();
        if(userAddress == null)
            throw new NoUserInformationException("No Address Information");

        return UserProfileConverter.entityToAddressResponseModel(userAddress);
    }

    @Override
    public List<FamilyDetailsResponse> updateFamilyDetails(FamilyDetailsRequest familyDetailsRequest) {
        User user = getUser(familyDetailsRequest.getUserId());

        FamilyInformation familyInformation = UserProfileConverter.modelToFamilyInformationEntity(familyDetailsRequest);

        user.getPersonalInformation().getFamilyInformation().add(familyInformation);
        familyInformation.setPersonalInformation(user.getPersonalInformation());
        user = userDao.save(user);

        return UserProfileConverter.entityToFamilyInformationResponseModel(user.getPersonalInformation().getFamilyInformation());
    }

    @Override
    public List<FamilyDetailsResponse> getFamilyDetails(UUID userId) {
        User user = getUser(userId);

        PersonalInformation personalInformation = user.getPersonalInformation();
        if(personalInformation == null)
            throw new NoUserInformationException("No Personal Information");

        List<FamilyInformation> familyInformationList = personalInformation.getFamilyInformation();
        if(familyInformationList.isEmpty())
            throw new NoUserInformationException("No Family Information");

        return UserProfileConverter.entityToFamilyInformationResponseModel(user.getPersonalInformation().getFamilyInformation());
    }

    private User getUser(UUID userId) {
        User user = userDao.findById(userId).orElseThrow(() -> {throw new UserNotFoundException("User does not exist");});
        return user;
    }
}
