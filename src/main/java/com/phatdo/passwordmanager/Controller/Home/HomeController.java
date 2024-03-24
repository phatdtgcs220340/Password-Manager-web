package com.phatdo.passwordmanager.Controller.Home;

import com.phatdo.passwordmanager.Entity.Account.AccountService;
import com.phatdo.passwordmanager.Entity.Application.ApplicationService;
import com.phatdo.passwordmanager.Entity.User.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {
    private AccountService accountService;
    private ApplicationService applicationService;
    @Autowired
    public HomeController(ApplicationService applicationService, AccountService accountService) {
        this.accountService = accountService;
        this.applicationService = applicationService;
    }
    @ModelAttribute(name = "addApplicationForm")
    public AddApplicationForm addApplicationForm() {
        return new AddApplicationForm();
    }
    @GetMapping()
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("full_name", user.getFullName());
        model.addAttribute("application_list", applicationService.listApplication(user));
        return "home/home";
    }
    @PostMapping("/addApplication")
    public String addApplication(@AuthenticationPrincipal User user,
                                 @ModelAttribute AddApplicationForm addApplicationForm) {
        accountService.addApplication(addApplicationForm.toAccount(user));
        return "redirect:/home";
    }
}
