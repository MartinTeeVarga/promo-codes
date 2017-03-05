package com.czequered.promocodes.service;

import com.czequered.promocodes.model.User;
import com.czequered.promocodes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Martin Varga
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override public User getUser(String userId) {
        return repository.findByUserId(userId);
    }

    @Override public User saveUser(User user) {
        return repository.save(user);
    }
}
