package com.bmt.user.service.dao;

import com.common.model.user.User;
import com.common.model.user.UserStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao extends CrudRepository<User, UUID> {
    List<User> findAll();
    List<User> findAllByStatus(UserStatus status);
    Optional<User> findByEmailOrPhoneOrUsername(String email, String phone, String username);
    List<User> findAllByCreatedAtBetween(Date startDate, Date endDate);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findByUsername(String username);
}
