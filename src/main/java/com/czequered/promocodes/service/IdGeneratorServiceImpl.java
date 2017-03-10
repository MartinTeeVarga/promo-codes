package com.czequered.promocodes.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Martin Varga
 */
@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {

    private SecureRandom random = new SecureRandom();

    @Override
    public String generate() {
        return new BigInteger(130, random).toString(32).toUpperCase();
    }
}
