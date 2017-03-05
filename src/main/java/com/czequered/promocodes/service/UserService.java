package com.czequered.promocodes.service;

import com.czequered.promocodes.model.User;

/**
 * @author Martin Varga
 */
public interface UserService {
    User getUser(String userId);

    User saveUser(User user);
}
