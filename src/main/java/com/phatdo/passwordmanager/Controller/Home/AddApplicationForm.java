package com.phatdo.passwordmanager.Controller.Home;

import com.phatdo.passwordmanager.Entity.Account.Account;
import com.phatdo.passwordmanager.Entity.Application.Application;
import com.phatdo.passwordmanager.Entity.User.User;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
public class AddApplicationForm {
    private String applicationName;
    private String url;
    private String username;
    private String password;
    public Account toAccount(User user) {
        Account account = new Account(username, password, Timestamp.from(Instant.now()), user);
        account.setApplication(new Application(applicationName, url));
        return account;
    }
}
