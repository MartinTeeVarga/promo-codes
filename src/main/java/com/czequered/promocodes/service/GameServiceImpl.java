package com.czequered.promocodes.service;

import com.czequered.promocodes.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Martin Varga
 */
@Service
public class GameServiceImpl implements GameService {
    private GameRepository repository;

    @Autowired
    public GameServiceImpl(GameRepository repository) {
        this.repository = repository;
    }
}
