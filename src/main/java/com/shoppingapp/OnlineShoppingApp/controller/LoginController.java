package com.shoppingapp.OnlineShoppingApp.controller;

import com.shoppingapp.OnlineShoppingApp.model.UserInfo;
import com.shoppingapp.OnlineShoppingApp.model.dto.AuthRequest;
import com.shoppingapp.OnlineShoppingApp.model.dto.Reset;
import com.shoppingapp.OnlineShoppingApp.service.JwtService;
import com.shoppingapp.OnlineShoppingApp.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/shopping")
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final LoginService loginService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public UserInfo addNewUser(@RequestBody UserInfo userInfo) throws RuntimeException {
        return loginService.addUser(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLoginId(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(jwtService.generateToken(authRequest.getLoginId()));
        } else {
            throw new UsernameNotFoundException("invalid user request!");
        }
    }


    @GetMapping("/forgot/{email}")
    public ResponseEntity<?> processForgotPassword(@PathVariable String email) throws Exception {
        String token = jwtService.generateToken(email);
        try {
            loginService.updateResetPasswordToken(token, email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    @PostMapping("/reset_password")
    public ResponseEntity<?> processResetPassword(@RequestBody Reset reset) {

        UserInfo userInfo = loginService.getByResetPasswordToken(reset.getToken());

        if (userInfo == null) {
            return new ResponseEntity<>("Invalid Token", HttpStatus.BAD_REQUEST);
        } else {
            loginService.updatePassword(userInfo, reset.getPassword());
        }
        return new ResponseEntity<>("You have successfully changed your password.", HttpStatus.OK);
    }
}
