package com.czequered.promocodes.controller;

import com.czequered.promocodes.service.ClockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

/**
 * @author Martin Varga
 */
@Profile("integrationtest")
@Configuration
public class IntegrationTestConfiguration extends RestTestConfiguration {
    @Bean
    @Primary
    public ClockService clockService() {
        return mock(ClockService.class);
    }
}
