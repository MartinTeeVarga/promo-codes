package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Game;
import com.czequered.promocodes.service.GameService;
import com.czequered.promocodes.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Martin Varga
 */
@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private GameService gameService;

    private TokenService tokenService;

    @Autowired
    public GameController(GameService gameService, TokenService tokenService) {
        this.gameService = gameService;
        this.tokenService = tokenService;
    }

    @RequestMapping(value = "/list",
        method = GET,
        produces = APPLICATION_JSON_VALUE)
    public HttpEntity<List<Game>> list(@RequestHeader(name = TOKEN_HEADER) String token) {
        String userIdFromToken = tokenService.getUserIdFromToken(token);
        List<Game> games = gameService.getGames(userIdFromToken);
        return new HttpEntity<>(games);
    }

    @RequestMapping(value = "/{gameId}",
        method = GET,
        produces = APPLICATION_JSON_VALUE)
    public HttpEntity<Game> getCode(@RequestHeader(name = TOKEN_HEADER) String token,
                                    @PathVariable("gameId") String gameId) {
        String userIdFromToken = tokenService.getUserIdFromToken(token);
        Game game = gameService.getGame(userIdFromToken, gameId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        return new HttpEntity<>(game);
    }
}
