package com.phatdo.passwordmanager.Controller.Home;

import com.phatdo.passwordmanager.Entity.Account.Account;
import com.phatdo.passwordmanager.Entity.Account.AccountService;
import com.phatdo.passwordmanager.Entity.Application.ApplicationService;
import com.phatdo.passwordmanager.Entity.User.User;
import com.phatdo.passwordmanager.Exception.AccountExistedException;
import com.phatdo.passwordmanager.Exception.AccountNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String home(@RequestParam(name = "applicationId", required = false) Integer applicationId,
            @AuthenticationPrincipal User user, Model model) {
        if (applicationId != null)
            try {
                Account account = accountService.findAccount(user.getId(), applicationId);
                model.addAttribute("accountInfo", account);
            } catch (EmptyResultDataAccessException e) {
                return "redirect:/home?error=userNotFound";
            }
        else
            model.addAttribute("accountInfo", new Account());
        model.addAttribute("full_name", user.getFullName());
        model.addAttribute("application_list", applicationService.listApplication(user));
        return "home/home";
    }

    @PostMapping("/addApplication")
    public String addApplication(@AuthenticationPrincipal User user,
            @ModelAttribute AddApplicationForm addApplicationForm) {
        try {
            accountService.addApplication(addApplicationForm.toAccount(user));
        } catch (AccountExistedException e) {
            return "redirect:/home?error=accountExisted";
        }
        return "redirect:/home";
    }

    @PatchMapping("/updateApplication")
    public String updateApplication(@AuthenticationPrincipal User user,
            @RequestParam(name = "accountId") Integer accountId,
            @RequestParam(name = "newPassword") String newPassword) {
        try {
            accountService.updateAccount(user, accountId, newPassword);
        } catch (AccountNotFoundException e) {
            return "redirect:/home?error=accountNotFound";
        }
        return "redirect:/home";
    }

    @DeleteMapping("/delete")
    public String deleteApplication(@AuthenticationPrincipal User user,
            @RequestParam(name = "accountId") Integer accountId) {
        System.out.println(accountId);
        accountService.deleteAssociation(user, accountId);
        user.getApplications().forEach(application -> System.out.println(application.getApplicationName()));
        return "redirect:/home";
    }
}
