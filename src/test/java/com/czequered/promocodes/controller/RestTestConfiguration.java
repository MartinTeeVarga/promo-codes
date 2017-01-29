package com.czequered.promocodes.controller;

import com.czequered.promocodes.service.CodeService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * @author Martin Varga
 */
@Profile("resttest")
@Configuration
public class RestTestConfiguration {
    @Bean
    @Primary
    public CodeService codeService() {
        return Mockito.mock(CodeService.class);
    }
}
