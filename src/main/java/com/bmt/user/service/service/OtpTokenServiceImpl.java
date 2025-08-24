package com.bmt.user.service.service;

import com.bmt.user.service.dao.OtpTokenDao;
import com.bmt.model.user.OtpToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpTokenServiceImpl implements OtpTokenService {
    private final OtpTokenDao otpTokenDao;

    @Autowired
    public OtpTokenServiceImpl(OtpTokenDao otpTokenDao) {
        this.otpTokenDao = otpTokenDao;
    }
    @Override
    public OtpToken updateOtpTokenStatus(OtpToken otpToken) {
        return otpTokenDao.save(otpToken);
    }
}
