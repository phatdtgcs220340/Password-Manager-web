package com.phatdo.passwordmanager.Config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.phatdo.passwordmanager.Config.Security.CustomOAuth2.CustomOAuth2User;
import com.phatdo.passwordmanager.Entity.User.UserService;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
        @Autowired
        private UserService userService;
        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws ServletException, IOException {
                CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                super.onAuthenticationSuccess(request, response, authentication);
                // save user after authentication
                userService.processOAuthPostLogin(oAuth2User.getEmail(), oAuth2User.getName());

        }

        protected void handle(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Authentication authentication) throws IOException {

                String targetUrl = determineTargetUrl(authentication);

                if (response.isCommitted()) {
                        logger.debug(
                                        "Response has already been committed. Unable to redirect to "
                                                        + targetUrl);
                        return;
                }

                redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        protected String determineTargetUrl(final Authentication authentication) {
                Map<String, String> roleTargetUrlMap = new HashMap<>();
                roleTargetUrlMap.put("ROLE_USER", "/home");

                final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                for (final GrantedAuthority grantedAuthority : authorities) {
                        String authorityName = grantedAuthority.getAuthority();
                        if (roleTargetUrlMap.containsKey(authorityName)) {
                                return roleTargetUrlMap.get(authorityName);
                        }
                }

                throw new IllegalStateException();
        }
}
