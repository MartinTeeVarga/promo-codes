package com.czequered.promocodes.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Varga
 */
public class ClockServiceImplTest {
    @Test
    public void getClockTest() throws Exception {
        ClockServiceImpl clockService = new ClockServiceImpl();
        assertThat(clockService.getClock().millis()).isLessThanOrEqualTo(System.currentTimeMillis());
    }
}