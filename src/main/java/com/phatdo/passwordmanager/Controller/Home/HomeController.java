package com.phatdo.passwordmanager.Controller.Home;

import com.phatdo.passwordmanager.Entity.Application.Application;
import com.phatdo.passwordmanager.Entity.User.User;
import com.phatdo.passwordmanager.Entity.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }
    @ModelAttribute(name = "addApplicationForm")
    public AddApplicationForm addApplicationForm() {
        return new AddApplicationForm();
    }
    @GetMapping()
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("full_name", user.getFullName());
        model.addAttribute("application_list", userService.listApplication(user));
        return "home/home";
    }
    @PostMapping("/addApplication")
    public String addApplication(@AuthenticationPrincipal User user,
                                 @ModelAttribute AddApplicationForm addApplicationForm) {
        userService.addApplication(user, addApplicationForm.toApplication());
        return "redirect:/home";
    }
}
