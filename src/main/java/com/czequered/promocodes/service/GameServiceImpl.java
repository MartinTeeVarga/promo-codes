package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Game;
import com.czequered.promocodes.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override public List<Game> getGames(String userId) {
        return repository.findByUserId(userId);
    }

    @Override public Game getGame(String userId, String gameId) {
        return repository.findByUserIdAndGameId(userId, gameId);
    }

    @Override public void deleteGame(String user, String game) {
        repository.delete(new Game(user, game));
    }

    @Override
    public Game saveGame(Game game) {
        return repository.save(game);
    }
}
