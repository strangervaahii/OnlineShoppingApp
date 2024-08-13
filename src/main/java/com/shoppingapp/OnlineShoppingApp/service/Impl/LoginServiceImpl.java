package com.shoppingapp.OnlineShoppingApp.service.Impl;

import com.shoppingapp.OnlineShoppingApp.model.UserInfo;
import com.shoppingapp.OnlineShoppingApp.repository.ProductRepository;
import com.shoppingapp.OnlineShoppingApp.repository.UserInfoRepository;
import com.shoppingapp.OnlineShoppingApp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserInfo addUser(UserInfo userInfo) throws RuntimeException {
        if (!userInfo.getPassword().equals(userInfo.getConfirmPassword())) {
            throw new RuntimeException();
        }
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setConfirmPassword(passwordEncoder.encode(userInfo.getConfirmPassword()));
        userInfoRepository.save(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo getByResetPasswordToken(String token) {
        return userInfoRepository.findByResetPasswordToken(token);
    }


    @Override
    public void updateResetPasswordToken(String token, String email) throws Exception {
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        if (userInfo != null) {
            userInfo.setResetPasswordToken(token);
            userInfoRepository.save(userInfo);
        } else {
            throw new Exception("Could not find any user with email " + email);
        }
    }

    @Override
    public void updatePassword(UserInfo userInfo, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        userInfo.setPassword(encodedPassword);

        userInfo.setResetPasswordToken(null);
        userInfoRepository.save(userInfo);
    }
}
