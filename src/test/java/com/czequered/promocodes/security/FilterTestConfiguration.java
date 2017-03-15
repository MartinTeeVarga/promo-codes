package com.czequered.promocodes.security;

import com.czequered.promocodes.service.ClockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

/**
 * @author Martin Varga
 */
@Profile("filtertest")
@Configuration
public class FilterTestConfiguration {
    @Bean
    @Primary
    public ClockService clockService() {
        return mock(ClockService.class);
    }
}
