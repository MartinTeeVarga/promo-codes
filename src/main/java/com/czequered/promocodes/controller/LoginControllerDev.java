package com.czequered.promocodes.controller;

import com.czequered.promocodes.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;

/**
 * @author Martin Varga
 */
@RestController
@Profile("dev")
public class LoginControllerDev {
    Logger logger = LoggerFactory.getLogger(LoginControllerDev.class);

    @Value("${jepice.frontend.url:none}")
    private String corsUrl;

    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/dev", method = RequestMethod.GET)
    public void login(HttpServletResponse response, @RequestParam String userId) {
        logger.info("Log in as {}", userId);
        String token = tokenService.generateToken(userId);
        response.addCookie(new Cookie(TOKEN_HEADER, token));
        try {
            response.sendRedirect(corsUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
