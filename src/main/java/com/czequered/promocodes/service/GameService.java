package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Game;

import java.util.List;

/**
 * @author Martin Varga
 */
public interface GameService {
    List<Game> getGames(String userId);

    Game getGame(String userId, String gameId);

    void deleteGame(String userId, String gameId);

    Game saveGame(Game game);
}
