package com.shoppingapp.OnlineShoppingApp.service;

import com.shoppingapp.OnlineShoppingApp.config.UserInfoUserDetails;
import com.shoppingapp.OnlineShoppingApp.model.UserInfo;
import com.shoppingapp.OnlineShoppingApp.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repository.findByLoginId(loginId);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user id not found" + loginId));
    }
}
