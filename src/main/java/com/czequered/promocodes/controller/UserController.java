package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.User;
import com.czequered.promocodes.service.InvalidTokenException;
import com.czequered.promocodes.service.TokenService;
import com.czequered.promocodes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Martin Varga
 */
@RestController
public class UserController {

    private UserService userService;

    private TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @RequestMapping(value = "/api/v1/user",
        method = GET,
        produces = APPLICATION_JSON_VALUE)
    public HttpEntity<User> getUser(@RequestHeader(TOKEN_HEADER) String token) {
        try {
            String userIdFromToken = tokenService.getUserIdFromToken(token);
            User user = userService.getUser(userIdFromToken);
            return new HttpEntity<>(user);
        } catch (InvalidTokenException e) {
            throw new InvalidTokenHeaderException();
        }
    }
}
