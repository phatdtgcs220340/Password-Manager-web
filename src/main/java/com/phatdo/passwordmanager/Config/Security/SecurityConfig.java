package com.phatdo.passwordmanager.Config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import com.phatdo.passwordmanager.Config.Security.CustomOAuth2.CustomOAuth2UserService;
import com.phatdo.passwordmanager.Entity.User.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
        private OAuth2SuccessHandler oAuth2SuccessHandler;
        private CustomOAuth2UserService customOAuth2UserService;

        @Autowired
        public SecurityConfig(OAuth2SuccessHandler oauth2SuccessHandler,
                        CustomOAuth2UserService customOAuth2UserService) {
                this.oAuth2SuccessHandler = oauth2SuccessHandler;
                this.customOAuth2UserService = customOAuth2UserService;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/home", "home**").hasAuthority("ROLE_USER")
                                .requestMatchers("/", "/**").permitAll()
                                .anyRequest().authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .successHandler(oAuth2SuccessHandler)
                                                .userInfoEndpoint(endpoint -> endpoint
                                                                .userService(customOAuth2UserService)))
                                .exceptionHandling(e -> e
                                                .authenticationEntryPoint(
                                                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .csrf(AbstractHttpConfigurer::disable);
                return http.build();
        }

}
