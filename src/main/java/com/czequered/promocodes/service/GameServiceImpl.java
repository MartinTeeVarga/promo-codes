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
    private IdGeneratorService idGeneratorService;

    @Autowired
    public GameServiceImpl(GameRepository repository, IdGeneratorService idGeneratorService) {
        this.repository = repository;
        this.idGeneratorService = idGeneratorService;
    }

    @Override public List<Game> getGames(String userId) {
        return repository.findByUserId(userId);
    }

    @Override public Game getGame(String userId, String gameId) {
        return repository.findByUserIdAndGameId(userId, gameId);
    }

    @Override
    public void deleteGame(String userId, String gameId) {
        repository.delete(new Game(userId, gameId));
    }

    @Override
    public Game saveGame(Game game) {
        while (game.getGameId() == null) {
            String gameIdCandidate = idGeneratorService.generate();
            Game byUserIdAndGameId = repository.findByUserIdAndGameId(game.getUserId(), gameIdCandidate);
            if (byUserIdAndGameId == null) {
                game.setGameId(gameIdCandidate);
            }
        }
        return repository.save(game);
    }
}
