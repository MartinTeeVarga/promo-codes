package com.czequered.promocodes.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Varga
 */
public class IdGeneratorServiceImplTest {
    @Test
    public void generate() throws Exception {
        IdGeneratorService idGeneratorService = new IdGeneratorServiceImpl();
        assertThat(idGeneratorService.generate())
            .hasSize(26);
    }

}