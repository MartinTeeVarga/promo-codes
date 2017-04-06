package com.czequered.promocodes.service;

import com.czequered.promocodes.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Varga
 */
@Service
@Profile("dev")
@Primary
public class UserServiceDev implements UserService {

    Map<String, User> localCache;

    public UserServiceDev() {
        localCache = new HashMap<>();
        User krtek = new User();
        krtek.setId("Krtek");
        krtek.addAttribute("name", "Krteček");
        krtek.addAttribute("credit", "10");
        localCache.put("Krtek", krtek);

        User sova = new User();
        sova.setId("Sova");
        localCache.put("Sova", sova);
    }

    @Override public User getUser(String userId) {
        return localCache.get(userId);
    }

    @Override public User saveUser(User user) {
        localCache.put(user.getId(), user);
        return user;
    }
}
