package com.czequered.promocodes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martin Varga
 */
@RestController
@RequestMapping("/api/v1/games")
public class GameController {
//
//    private GameService gameService;
//
//    private TokenService tokenService;
//
//    @Autowired
//    public GameController(GameService gameService, TokenService tokenService) {
//        this.gameService = gameService;
//    }
//
//    @RequestMapping(value = "/list",
//            method = GET,
//            produces = APPLICATION_JSON_VALUE)
//    public HttpEntity<List<Game>> list(@RequestHeader(name = TOKEN_HEADER) String token) {
//        // inspect token
//        // do something
//        try {
//            String userIdFromToken = tokenService.getUserIdFromToken(token);
//            List<Game> games = gameService.getGames(userIdFromToken);
//            return new HttpEntity<>(games);
//        } catch (InvalidTokenException e) {
//            throw new UnathorizedException
//        }
//    }
//
//    @RequestMapping(value = "/{gameId}",
//            method = GET,
//            produces = APPLICATION_JSON_VALUE)
//    public HttpEntity<Code> getCode(@PathVariable("gameId") String gameId) {
//        Code retrieved = gameService.getGame(gameId, code);
//        if (retrieved == null) {
//            throw new CodeNotFoundException();
//        }
//        return new HttpEntity<>(retrieved);
//    }
}
