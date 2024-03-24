package com.phatdo.passwordmanager.Controller.Auth.Register;

import com.phatdo.passwordmanager.Entity.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/register")
@SessionAttributes("registerForm")
public class RegisterController {
    private PasswordEncoder encoder;
    private UserService userService;

    @Autowired
    public RegisterController(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    @ModelAttribute(name = "registerForm")
    public RegisterForm registerForm() {
        return new RegisterForm();
    }

    @GetMapping()
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping()
    public String processRegister(@ModelAttribute RegisterForm registerForm, Errors errors) {
        System.out.println("concac");
        if (errors.hasErrors())
            return "auth/register";
        try {
            userService.registerUser(registerForm.toUser(encoder));
        } catch (Exception e) {
            return "redirect:/register?error";
        }
        log.info(String.valueOf(registerForm));
        return "redirect:/login";
    }
}
