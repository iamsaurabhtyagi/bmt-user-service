package com.bmt.user.service.service;

import com.bmt.exception.UserAlreadyRegisteredException;
import com.common.model.user.*;
import com.bmt.user.service.controller.converter.UnverifiedUserConverter;
import com.bmt.user.service.dao.UnverifiedUserDao;
import com.bmt.user.service.security.OtpGenerator;
import com.bmt.user.service.view.request.OTPRequest;
import com.bmt.user.service.view.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UnverifiedUserServiceImpl implements UnverifiedUserService {

    private final OtpGenerator otpGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UnverifiedUserDao unverifiedUserDao;
    private final OtpTokenService otpTokenService;

    @Autowired
    public UnverifiedUserServiceImpl(OtpGenerator otpGenerator,
                                     PasswordEncoder passwordEncoder,
                                     UnverifiedUserDao unverifiedUserDao,
                                     OtpTokenService otpTokenService) {
        this.otpGenerator = otpGenerator;
        this.passwordEncoder = passwordEncoder;
        this.unverifiedUserDao = unverifiedUserDao;
        this.otpTokenService = otpTokenService;
    }
    @Override
    public String register(UserRequest userRequest) {
        UnverifiedUser unverifiedUser = UnverifiedUserConverter.requestToEntityUnverifiedUser(userRequest);

        OtpToken otpToken = getOtpDataForOtpToken();
        otpToken.setTokenStatus(TokenStatus.NOT_USED);
        otpToken.setPurpose(OtpPurpose.REGISTRATION);
        otpToken.setUnverifiedUser(unverifiedUser);

        unverifiedUser.setOtpTokens(List.of(otpToken));
        unverifiedUser.setVerified(false);
        unverifiedUser.setPassword(passwordEncoder.encode(unverifiedUser.getPassword()));

        //TODO: send otp to mobile or email

        unverifiedUserDao.save(unverifiedUser);
        return "OTP sent to your Mobile";
    }

    @Override
    public String resendOTP(OTPRequest otpRequest) {
        UnverifiedUser unverifiedUser = getUnverifiedUser(otpRequest.getEmail(), otpRequest.getPhone());

        boolean flag = canOtpSendFor(unverifiedUser.getOtpTokens(), OtpPurpose.REGISTRATION, TokenStatus.USED);
        if(flag && OtpPurpose.REGISTRATION.equals(otpRequest.getOtpPurpose()))
            throw new UserAlreadyRegisteredException("User Already Registered");

        List<OtpToken> otpTokens = unverifiedUser.getOtpTokens();
        invalidateNotUsedToken(otpTokens);

        OtpToken otpToken = getOtpDataForOtpToken();
        otpToken.setTokenStatus(TokenStatus.NOT_USED);
        otpToken.setPurpose(otpRequest.getOtpPurpose() == null ? OtpPurpose.REGISTRATION : otpRequest.getOtpPurpose());
        otpToken.setUnverifiedUser(unverifiedUser);

        otpTokens.add(otpToken);

        //TODO: send otp to mobile or email

        unverifiedUserDao.save(unverifiedUser);
        return "OTP sent to your Mobile";
    }

    private boolean canOtpSendFor(List<OtpToken> otpTokens, OtpPurpose otpPurpose, TokenStatus tokenStatus) {

        return otpTokens.stream()
                .anyMatch(otpToken -> otpPurpose.equals(otpToken.getPurpose()) && tokenStatus.equals(otpToken.getTokenStatus()));

    }

    @Override
    public String updateUserMobile(UUID userId, UserRequest request) {
        UnverifiedUser unverifiedUser = unverifiedUserDao.findByUserId(userId)
                .orElseThrow(() -> {throw new RuntimeException("Invalid User");});

        UserLoginUpdateDetails userLoginUpdateDetails = UnverifiedUserConverter.requestToEntityForUserLoginUpdateRequest(request);

        OtpToken otpToken = getOtpDataForOtpToken();
        otpToken.setPurpose(OtpPurpose.UPDATE_MOBILE);
        otpToken.setTokenStatus(TokenStatus.NOT_USED);
        otpToken.setUsed(false);

        otpToken.setUnverifiedUser(unverifiedUser);

        userLoginUpdateDetails.setOldPhone(unverifiedUser.getPhone());
        userLoginUpdateDetails.setUnverifiedUser(unverifiedUser);

        unverifiedUser.getOtpTokens().add(otpToken);
        unverifiedUser.getLoginUpdateRequests().add(userLoginUpdateDetails);

        //TODO: Send OTP to Email

        unverifiedUserDao.save(unverifiedUser);

        return "OTP send to Mobile";
    }

    @Override
    public String updateUserEmail(UUID userId, UserRequest request) {
        UnverifiedUser unverifiedUser = unverifiedUserDao.findByUserId(userId)
                .orElseThrow(() -> {throw new RuntimeException("Invalid User");});

        UserLoginUpdateDetails userLoginUpdateDetails = UnverifiedUserConverter.requestToEntityForUserLoginUpdateRequest(request);

        OtpToken otpToken = getOtpDataForOtpToken();
        otpToken.setPurpose(OtpPurpose.UPDATE_EMAIL);
        otpToken.setTokenStatus(TokenStatus.NOT_USED);
        otpToken.setUsed(false);

        otpToken.setUnverifiedUser(unverifiedUser);

        userLoginUpdateDetails.setOldEmail(unverifiedUser.getEmail());
        userLoginUpdateDetails.setUnverifiedUser(unverifiedUser);

        unverifiedUser.getOtpTokens().add(otpToken);
        unverifiedUser.getLoginUpdateRequests().add(userLoginUpdateDetails);

        //TODO: Send OTP to Email

        unverifiedUserDao.save(unverifiedUser);

        return "OTP send to Mobile";
    }

    @Override
    public UnverifiedUser saveOrUpdate(UnverifiedUser unverifiedUser) {
        return unverifiedUserDao.save(unverifiedUser);
    }

    @Override
    public UnverifiedUser findUnverifiedUserByEmailOrPhone(String email, String phone) {
        return unverifiedUserDao.findByEmailOrPhone(email, phone)
                .orElseThrow(() -> {throw new RuntimeException("Invalid User");});
    }

    private OtpToken getOtpDataForOtpToken() {
        OtpGenerator.OtpData otpData = otpGenerator.generateOtpWithExpiry();

        OtpToken otpToken = new OtpToken();
        otpToken.setOtp(otpData.otp());
        otpToken.setExpiryTime(otpData.expiryTime());

        return otpToken;
    }

    private UnverifiedUser getUnverifiedUser(String email, String phone) {
        return unverifiedUserDao.findByEmailOrPhone(email, phone)
                .orElseThrow(() -> {throw new RuntimeException("Invalid User");});
    }

    private void invalidateNotUsedToken(List<OtpToken> otpTokens) {
        otpTokens.forEach(token -> {
            if(token.getTokenStatus().equals(TokenStatus.NOT_USED)) {
                token.setTokenStatus(TokenStatus.INVALID);
                otpTokenService.updateOtpTokenStatus(token);
            }
        });
    }
}
