package com.phatdo.passwordmanager.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.phatdo.passwordmanager.Entity.User.User;
import com.phatdo.passwordmanager.Entity.User.UserRepository;

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
        private UserRepository userRepository;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws ServletException, IOException {
                OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                if ("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
                        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
                        Map<String, Object> attributes = principal.getAttributes();
                        String email = attributes.getOrDefault("email", "").toString();
                        String name = attributes.getOrDefault("name", "").toString();
                        userRepository.findByUsername(email)
                                        .ifPresentOrElse(user -> {
                                                DefaultOAuth2User newUser = new DefaultOAuth2User(
                                                                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                                                                attributes, "id");
                                                Authentication securityAuth = new OAuth2AuthenticationToken(newUser,
                                                                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                                                                oAuth2AuthenticationToken
                                                                                .getAuthorizedClientRegistrationId());
                                                SecurityContextHolder.getContext().setAuthentication(securityAuth);
                                        }, () -> {
                                                User userEntity = new User(name, email);

                                                userRepository.save(userEntity);
                                                DefaultOAuth2User newUser = new DefaultOAuth2User(
                                                                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                                                                attributes, "id");
                                                Authentication securityAuth = new OAuth2AuthenticationToken(newUser,
                                                                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                                                                oAuth2AuthenticationToken
                                                                                .getAuthorizedClientRegistrationId());
                                                SecurityContextHolder.getContext().setAuthentication(securityAuth);
                                        });
                }
                super.onAuthenticationSuccess(request, response, authentication);
        }
}
