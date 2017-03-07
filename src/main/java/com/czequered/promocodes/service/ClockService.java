package com.czequered.promocodes.service;

import org.springframework.stereotype.Service;

import java.time.Clock;

/**
 * @author Martin Varga
 */
@Service
public interface ClockService {
    Clock getClock();
}
