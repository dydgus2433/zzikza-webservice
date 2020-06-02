package com.zzikza.springboot.web.domain.user;

import com.zzikza.springboot.web.domain.enums.ESnsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserIdAndPassword(String userId, String password);

    Optional<User> findByUserId(String userId);

    Optional<User> findByTel(String tel);

    Optional<User> findByUserIdAndSnsType(String userId, ESnsType snsType);
}

