package com.phatdo.passwordmanager.Config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.phatdo.passwordmanager.Config.Security.CustomOAuth2.CustomOAuth2User;
import com.phatdo.passwordmanager.Entity.User.User;
import com.phatdo.passwordmanager.Entity.User.UserRepository;
import com.phatdo.passwordmanager.Entity.User.UserService;

import java.util.Map;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
        @Autowired
        private UserService userService;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws ServletException, IOException {
                System.out.println("Chay duoc successHandler roi ne");
                CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                super.onAuthenticationSuccess(request, response, authentication);
                userService.processOAuthPostLogin(oAuth2User.getEmail(), oAuth2User.getName());
                System.out.println("hehe xong successhandler roi");
        }
}
