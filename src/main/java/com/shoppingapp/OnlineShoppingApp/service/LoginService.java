package com.shoppingapp.OnlineShoppingApp.service;

import com.shoppingapp.OnlineShoppingApp.model.UserInfo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LoginService {
    public UserInfo addUser(UserInfo userInfo);
    public UserInfo getUser(String loginId) throws UsernameNotFoundException;

//    public void updateResetPasswordToken(String token, String email) throws Exception;

    public UserInfo getByResetPasswordToken(String token);

    public void updatePassword(UserInfo userInfo, String newPassword);

    void updateResetPasswordToken(String token, String email) throws Exception;
}
