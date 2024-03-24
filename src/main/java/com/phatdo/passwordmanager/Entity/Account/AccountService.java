package com.phatdo.passwordmanager.Entity.Account;

import com.phatdo.passwordmanager.Entity.Application.Application;
import com.phatdo.passwordmanager.Entity.Application.ApplicationRepository;
import com.phatdo.passwordmanager.Entity.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private ApplicationRepository applicationRepository;
    public AccountService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    public void addApplication(Account account) {
        User user = account.getUser();
        Application application = account.getApplication();
        Optional<Application> optApplication = applicationRepository.findByApplicationName(application.getApplicationName());
        optApplication.ifPresentOrElse(existedApplication -> {
            account.setApplication(existedApplication);
            // update new assoc instance to assoc table
            existedApplication.getAccounts().add(account);
            applicationRepository.save(existedApplication);
        }, () -> {
            // add new application to application list
            user.getApplications().add(account.getApplication());
            // update new assoc instance to assoc table
            application.getAccounts().add(account);
            applicationRepository.save(application);
        });
        // add new application to application list
        user.getApplications().add(account.getApplication());
        user.getAccounts().add(account);
    }
}
