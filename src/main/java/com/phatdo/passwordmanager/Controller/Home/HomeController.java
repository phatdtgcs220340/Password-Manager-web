package com.phatdo.passwordmanager.Controller.Home;

import com.phatdo.passwordmanager.Config.Security.CustomOAuth2.CustomOAuth2User;
import com.phatdo.passwordmanager.Entity.Account.Account;
import com.phatdo.passwordmanager.Entity.Account.AccountService;
import com.phatdo.passwordmanager.Entity.Application.ApplicationService;
import com.phatdo.passwordmanager.Entity.User.User;
import com.phatdo.passwordmanager.Entity.User.UserService;
import com.phatdo.passwordmanager.Exception.AccountExistedException;
import com.phatdo.passwordmanager.Exception.AccountNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {
    private AccountService accountService;
    private ApplicationService applicationService;
    private UserService userService;

    @Autowired
    public HomeController(ApplicationService applicationService, AccountService accountService,
            UserService userService) {
        this.accountService = accountService;
        this.applicationService = applicationService;
        this.userService = userService;
    }

    @ModelAttribute(name = "addApplicationForm")
    public AddApplicationForm addApplicationForm() {
        return new AddApplicationForm();
    }

    @GetMapping()
    public String home(@RequestParam(name = "applicationId", required = false) Integer applicationId,
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            Model model) {
        log.info(oAuth2User.getEmail());
        try {
            User user = userService.getUser(oAuth2User);
            if (applicationId != null) {
                Account account = accountService.findAccount(user.getId(), applicationId);
                model.addAttribute("accountInfo", account);
            } else
                model.addAttribute("accountInfo", new Account());
            model.addAttribute("full_name", user.getName());
            model.addAttribute("application_list", applicationService.listApplication(user));
            return "home/home";
        } catch (EmptyResultDataAccessException e) {
            return "redirect:/home?error=userNotFound";
        } catch (UsernameNotFoundException e) {
            return "redirect:/login?error";
        }
    }

    @PostMapping("/addApplication")
    public String addApplication(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @ModelAttribute AddApplicationForm addApplicationForm) {
        try {
            User user = userService.getUser(oAuth2User);
            accountService.addApplication(addApplicationForm.toAccount(user));
        } catch (AccountExistedException e) {
            return "redirect:/home?error=accountExisted";
        }
        return "redirect:/home";
    }

    @PatchMapping("/updateApplication")
    public String updateApplication(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @RequestParam(name = "accountId") Integer accountId,
            @RequestParam(name = "newPassword") String newPassword) {
        try {
            User user = userService.getUser(oAuth2User);
            accountService.updateAccount(user, accountId, newPassword);
        } catch (AccountNotFoundException e) {
            return "redirect:/home?error=accountNotFound";
        }
        return "redirect:/home";
    }

    @DeleteMapping("/delete")
    public String deleteApplication(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @RequestParam(name = "accountId") Integer accountId) {
        System.out.println(accountId);
        User user = userService.getUser(oAuth2User);
        accountService.deleteAssociation(user, accountId);
        user.getApplications().forEach(application -> System.out.println(application.getApplicationName()));
        return "redirect:/home";
    }
}
