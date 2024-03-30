package com.phatdo.passwordmanager.Entity.User;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

import com.phatdo.passwordmanager.Config.Security.CustomOAuth2.CustomOAuth2User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService extends DefaultOAuth2UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository repo;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.repo = userRepository;
    }

    public User getUser(CustomOAuth2User oAuth2User) throws Exception {
        String email = oAuth2User.getEmail();
        logger.info("Attempting to retrieve user with email: " + email);

        Optional<User> optUser = repo.findByEmail(email);
        if (optUser.isPresent()) {
            logger.info("User found with email: " + email);
            return optUser.get();
        } else {
            logger.warning("User not found with email: " + email);
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void processOAuthPostLogin(String email, String name) {
        logger.info("Processing OAuth2 post login for email: " + email);

        User existUser = repo.findByEmail(email).orElse(null);

        if (existUser == null) {
            logger.info("Creating new user with email: " + email);
            User newUser = new User(name, email);
            repo.save(newUser);
            logger.info("New user created successfully");
        } else {
            logger.info("User already exists with email: " + email);
        }
    }
}
