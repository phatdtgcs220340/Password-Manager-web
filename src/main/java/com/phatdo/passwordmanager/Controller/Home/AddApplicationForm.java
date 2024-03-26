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
        Account account = new Account(username, Timestamp.from(Instant.now()), user);
        account.setPassword(password);
        account.setApplication(new Application(capitalize(applicationName), url));
        return account;
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        sb.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1));
        return sb.toString();
    }
}
