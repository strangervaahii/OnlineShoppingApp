package com.shoppingapp.OnlineShoppingApp.repository;

import com.shoppingapp.OnlineShoppingApp.model.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByEmail(String email);

    Optional<UserInfo> findByLoginId(String loginId);

    UserInfo findByResetPasswordToken(String token);
}
