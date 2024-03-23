package com.phatdo.passwordmanager.Controller.Auth.Register;

import com.phatdo.passwordmanager.Entity.User.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegisterForm {
    private String fullName;
    private String username;
    private String password;
    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(fullName, username, passwordEncoder.encode(password));
    }
}