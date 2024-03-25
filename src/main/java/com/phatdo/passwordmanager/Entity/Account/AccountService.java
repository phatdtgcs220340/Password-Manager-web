package com.phatdo.passwordmanager.Entity.Account;

import com.phatdo.passwordmanager.Entity.Application.Application;
import com.phatdo.passwordmanager.Entity.Application.ApplicationRepository;
import com.phatdo.passwordmanager.Entity.User.User;
import com.phatdo.passwordmanager.Entity.User.UserRepository;
import com.phatdo.passwordmanager.Exception.AccountExistedException;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private ApplicationRepository applicationRepository;
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    @Autowired
    public AccountService(ApplicationRepository applicationRepository,
            AccountRepository accountRepository,
            UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account findAccount(Integer userId, Integer applicationId) throws EmptyResultDataAccessException {
        Optional<Account> optAccount = accountRepository.findByUserIdAndApplicationId(userId, applicationId);
        return optAccount.map(account -> account)
                .orElseThrow(() -> new EmptyResultDataAccessException("Account not found", 1));
    }

    public void addApplication(Account account) throws AccountExistedException {
        User user = account.getUser();
        Application application = account.getApplication();
        // Check if the account is created

        Optional<Application> optApplication = applicationRepository
                .findByApplicationName(application.getApplicationName());
        if (optApplication.isPresent()) {
            if (accountRepository.findByUserIdAndApplicationId(user.getId(), optApplication.get().getId())
                    .isPresent()) {
                System.out.println("con cac");
                throw new AccountExistedException();
            }
        }
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
        if (!user.getApplications().contains(application))
            user.getApplications().add(account.getApplication());
        user.getAccounts().add(account);
    }

    @Transactional
    public void deleteAssociation(User user, Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        Application application = account.getApplication();
        user.getApplications().remove(application);
        application.getAccounts().remove(account);
        user.getAccounts().remove(account);
        userRepository.save(user);
    }
}
