package com.bmt.user.service.dao;

import com.common.model.user.UnverifiedUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UnverifiedUserDao extends CrudRepository<UnverifiedUser, UUID> {
    Optional<UnverifiedUser> findByEmailOrPhone(String email, String phone);
    Optional<UnverifiedUser> findByUserId(UUID userId);
}
