package com.czequered.promocodes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author Martin Varga
 */
@Configuration
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .antMatcher("/**")
            .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**")
                .permitAll()
            .anyRequest()
                .authenticated()
            .and().logout().logoutSuccessUrl("/").permitAll()
            .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        // @formatter:on
    }
}
