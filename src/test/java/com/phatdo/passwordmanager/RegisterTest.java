package com.phatdo.passwordmanager;

import com.phatdo.passwordmanager.User.User;
import com.phatdo.passwordmanager.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegisterTest {
    @Autowired
    private UserService userService;
    @Test
    public void registerAUser() throws Exception{
        userService.registerUser(new User("phatdo","hentaisucvat"));
    }
}
