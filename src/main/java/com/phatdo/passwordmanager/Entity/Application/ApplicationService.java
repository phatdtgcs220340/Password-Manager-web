package com.phatdo.passwordmanager.Entity.Application;

import com.phatdo.passwordmanager.Entity.User.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ApplicationService {
    public Set<Application> listApplication(User user) {
        return user.getApplications();
    }
}
