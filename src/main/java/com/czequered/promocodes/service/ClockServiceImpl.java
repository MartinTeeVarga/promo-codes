package com.czequered.promocodes.service;

import org.springframework.stereotype.Service;

import java.time.Clock;

/**
 * @author Martin Varga
 */
@Service
public class ClockServiceImpl implements ClockService {
    @Override public Clock getClock() {
        return Clock.systemUTC();
    }
}
