package com.czequered.promocodes.service;

import java.util.Date;

/**
 * @author Martin Varga
 */
public interface TokenService {
    String getUserIdFromToken(String token) throws InvalidTokenException;

    String generateToken(String userName);

    void validateToken(String token) throws InvalidTokenException;
}
