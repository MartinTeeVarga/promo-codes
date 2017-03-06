package com.czequered.promocodes.controller;

import com.czequered.promocodes.service.CodeService;
import com.czequered.promocodes.service.GameService;
import com.czequered.promocodes.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

/**
 * @author Martin Varga
 */
@Profile("resttest")
@Configuration
public class RestTestConfiguration {
    @Bean
    @Primary
    public CodeService codeService() {
        return mock(CodeService.class);
    }

    @Bean
    @Primary
    public GameService gameService() {
        return mock(GameService.class);
    }

    @Bean
    @Primary
    public UserService userService() {
        return mock(UserService.class);
    }
}
