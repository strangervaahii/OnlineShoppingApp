package com.shoppingapp.OnlineShoppingApp.service;

import com.shoppingapp.OnlineShoppingApp.model.UserInfo;

public interface LoginService {
    public UserInfo addUser(UserInfo userInfo);

//    public void updateResetPasswordToken(String token, String email) throws Exception;

    public UserInfo getByResetPasswordToken(String token);

    public void updatePassword(UserInfo userInfo, String newPassword);

    void updateResetPasswordToken(String token, String email) throws Exception;
}
