package com.shoppingapp.OnlineShoppingApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String loginId;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String email;
    private String roles;
    private String resetPasswordToken;
    private Long contactNumber;
}
