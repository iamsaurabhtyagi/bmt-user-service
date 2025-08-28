package com.bmt.user.service.dao;

import com.common.model.user.OtpToken;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OtpTokenDao extends CrudRepository<OtpToken, UUID> {
}
