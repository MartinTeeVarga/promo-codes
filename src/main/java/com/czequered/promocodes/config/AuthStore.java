package com.czequered.promocodes.config;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Varga
 */
@Component
public class AuthStore {

    Map<String, Authentication> map = new HashMap<>();

    public Authentication get(String key) {
        return map.get(key);
    }

    public void put(String key, Authentication auth) {
        map.put(key, auth);
    }
}
