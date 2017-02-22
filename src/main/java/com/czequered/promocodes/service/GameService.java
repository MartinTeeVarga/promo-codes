package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Game;

import java.util.List;

/**
 * @author Martin Varga
 */
public interface GameService {
    List<Game> getGames(String userId);

    Game getGame(String userId, String gameId);

    void deleteGame(String user, String game);

    void saveCode(Game code);
}
