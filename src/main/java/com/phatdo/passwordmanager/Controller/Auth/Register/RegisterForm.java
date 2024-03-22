package com.phatdo.passwordmanager.Controller.Auth.Register;

import com.phatdo.passwordmanager.User.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegisterForm {
    private String username;
    private String password;
    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password));
    }
}