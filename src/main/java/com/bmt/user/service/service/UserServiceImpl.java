package com.bmt.user.service.service;

import com.bmt.exception.InvalidOtpException;
import com.bmt.exception.OtpExpiredException;
import com.bmt.exception.UserNotFoundException;
import com.bmt.user.service.controller.converter.UserConverter;
import com.bmt.user.service.dao.UserDao;
import com.bmt.user.service.security.JwtUtil;
import com.bmt.user.service.view.request.AuthRequest;
import com.bmt.user.service.view.request.OTPRequest;
import com.bmt.model.user.*;
import com.bmt.user.service.view.request.UserRequest;
import com.bmt.user.service.view.response.AuthResponse;
import com.bmt.user.service.view.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final OtpTokenService otpTokenService;
    private final UnverifiedUserService unverifiedUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           OtpTokenService otpTokenService,
                           UnverifiedUserService unverifiedUserService,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userDao = userDao;
        this.otpTokenService = otpTokenService;
        this.unverifiedUserService = unverifiedUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /*@Override
    public AuthResponse authenticate(AuthRequest request) {
        User user = getUserByEmailOrPhoneOrUsername(request.getEmail(), request.getPhone(), request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        if (!user.getIsActive()) {
            throw new RuntimeException("User account is inactive");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }*/

    @Override
    @Transactional(noRollbackFor = {OtpExpiredException.class})
    public UserResponse verifyOTP(OTPRequest otpRequest) {
        UnverifiedUser unverifiedUser = getUnverifiedUser(otpRequest.getEmail(), otpRequest.getPhone());

        //TODO: Verify OTP valid or not
        validateOtpAndMarkUsed(otpRequest.getOtp(), unverifiedUser.getOtpTokens());

        unverifiedUser.setVerified(true);

        //TODO: Get user data from unverified_user table

        User user = userDao.save(buildUser(unverifiedUser));

        unverifiedUser.setUserId(user.getId());
        unverifiedUserService.saveOrUpdate(unverifiedUser);

        //TODO: insert user data in user table
        return UserConverter.entityToResponse(user);
    }

    @Override
    @Transactional(noRollbackFor = {OtpExpiredException.class})
    public UserResponse verifyOtpForEmailUpdate(OTPRequest otpRequest) {
        UnverifiedUser unverifiedUser = getUnverifiedUser(otpRequest.getEmail(), otpRequest.getPhone());

        //TODO: Verify OTP valid or not
        validateOtpAndMarkUsed(otpRequest.getOtp(), unverifiedUser.getOtpTokens());
        User user = getAndUpdateUserLoginDetails(unverifiedUser);

        userDao.save(user);
        unverifiedUser.setEmail(user.getEmail());
        unverifiedUserService.saveOrUpdate(unverifiedUser);

        //TODO: Send Notification to user

        return UserConverter.entityToResponse(user);
    }

    @Override
    @Transactional(noRollbackFor = {OtpExpiredException.class})
    public UserResponse verifyOtpForMobileUpdate(OTPRequest otpRequest) {
        UnverifiedUser unverifiedUser = getUnverifiedUser(otpRequest.getEmail(), otpRequest.getPhone());

        //TODO: Verify OTP valid or not
        validateOtpAndMarkUsed(otpRequest.getOtp(), unverifiedUser.getOtpTokens());
        User user = getAndUpdateUserLoginDetails(unverifiedUser);

        userDao.save(user);

        unverifiedUser.setPhone(user.getPhone());
        unverifiedUserService.saveOrUpdate(unverifiedUser);

        //TODO: Send Notification to user

        return UserConverter.entityToResponse(user);
    }

    @Override
    public UserResponse update(UUID id, UserRequest userRequest) {
        User existingUser = findById(id);

        User user = userDao.save(existingUser);

        return UserConverter.entityToResponse(user);
    }

    @Override
    public String updatePassword(UUID id, UserRequest request) {
        User existingUser = findById(id);

        existingUser = UserConverter.requestToEntityToResetPassword(existingUser, request);
        userDao.save(existingUser);

        return "Password updated Successfully!!!";
    }

    @Override
    public String updateProfilePic() {
        return null;
    }

    @Override
    public UserResponse delete(UUID id) {
        User existingUser = findById(id);
        existingUser.setStatus(UserStatus.DELETED);
        existingUser = userDao.save(existingUser);
        return UserConverter.entityToResponse(existingUser);
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user = findById(id);
        return UserConverter.entityToResponse(user);
    }

    @Override
    public UserResponse getUserByEmailOrPhoneOrUsername(UserRequest userRequest) {
        User user = getUserByEmailOrPhoneOrUsername(userRequest.getEmail(), userRequest.getMobile(), userRequest.getUsername());
        return UserConverter.entityToResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users = userDao.findAll();
        return users.stream()
                .map(UserConverter::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllByStatus(UserRequest userRequest) {
        List<User> users = userDao.findAllByStatus(userRequest.getUserStatus());
        return users.stream()
                .map(UserConverter::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllByCreatedAtDate(Date startDate, Date endDate) {
        List<User> users = userDao.findAllByCreatedAtBetween(startDate, endDate);
        return users.stream()
                .map(UserConverter::entityToResponse)
                .collect(Collectors.toList());
    }

    private User findById(UUID id) {
        return userDao.findById(id).orElseThrow(() -> {throw new UserNotFoundException("User does not exist");});
    }

    private User getAndUpdateUserLoginDetails(UnverifiedUser unverifiedUser) {
        User user = getUserByEmailOrPhoneOrUsername(unverifiedUser.getEmail(), unverifiedUser.getPhone(), unverifiedUser.getUsername());

        UserLoginUpdateDetails userLoginUpdateDetails = unverifiedUser.getLoginUpdateRequests()
                .stream()
                .filter(uu -> uu.getStatus().equals(RequestStatus.PENDING))
                .findAny()
                .orElseThrow(() -> {throw new RuntimeException("");});

        user.setEmail(userLoginUpdateDetails.getNewEmail());
        user.setPhone(userLoginUpdateDetails.getNewPhone());
        user.setUsername(userLoginUpdateDetails.getNewUsername());

        userLoginUpdateDetails.setStatus(RequestStatus.VERIFIED);
        userLoginUpdateDetails.setUnverifiedUser(unverifiedUser);

        return user;
    }
    private User getUserByEmailOrPhoneOrUsername(String email, String phone, String username) {
        return userDao.findByEmailOrPhoneOrUsername(email, phone, username)
                .orElseThrow(() -> {throw new UserNotFoundException("Get User: User Not Found");});
    }

    private UnverifiedUser getUnverifiedUser(String email, String phone) {
        return unverifiedUserService.findUnverifiedUserByEmailOrPhone(email, phone);
    }

    private User buildUser(UnverifiedUser unverifiedUser) {
        User user = new User();

        user.setEmail(unverifiedUser.getEmail());
        user.setPhone(unverifiedUser.getPhone());
        user.setUsername(unverifiedUser.getUsername());
        user.setPassword(unverifiedUser.getPassword());
        user.setPin(unverifiedUser.getPin());
        user.setStatus(UserStatus.ACTIVE);
        user.setIsActive(true);
        user.setVerified(unverifiedUser.getVerified());
        user.setUserType(unverifiedUser.getUserType());

        return user;
    }

    private boolean validateOtpAndMarkUsed(String otp, List<OtpToken> otpTokens) {
        OtpToken otpToken = getNotUsedOtpToken(otpTokens)
                .orElseThrow(() -> new InvalidOtpException("Error 1: Invalid Token"));

        if(!otpToken.getOtp().equals(otp))
            throw new InvalidOtpException("Error 2: Invalid Token");
        if(isOtpExpired(otpToken.getExpiryTime())) {
            otpToken.setTokenStatus(TokenStatus.EXPIRED);
            otpTokenService.updateOtpTokenStatus(otpToken);
            throw new OtpExpiredException("Error 3: Token Expired");
        }

        otpToken.setUsed(true);
        otpToken.setTokenStatus(TokenStatus.USED);

        return true;
    }

    private Optional<OtpToken> getNotUsedOtpToken(List<OtpToken> tokens) {
        return tokens.stream()
                .filter(token -> token.getTokenStatus().equals(TokenStatus.NOT_USED))
                .findAny();
    }

    private boolean isOtpExpired(LocalDateTime expiryTime) {
        return LocalDateTime.now().isAfter(expiryTime);
    }
}
