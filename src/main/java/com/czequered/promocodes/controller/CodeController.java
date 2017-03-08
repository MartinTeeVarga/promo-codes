package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.service.CodeService;
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
@RequestMapping("/api/v1/games/{gameId}/codes")
public class CodeController {

    private CodeService codeService;
    private TokenService tokenService;
    private GameService gameService;

    @Autowired
    public CodeController(GameService gameService, CodeService codeService, TokenService tokenService) {
        this.gameService = gameService;
        this.codeService = codeService;
        this.tokenService = tokenService;
    }

    @RequestMapping(value = "/list",
        method = GET,
        produces = APPLICATION_JSON_VALUE)
    public HttpEntity<List<Code>> list(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("gameId") String gameId) {
        String userIdFromToken = tokenService.getUserIdFromToken(token);
        if (gameService.getGame(userIdFromToken, gameId) == null) {
            throw new AccessForbiddenException();
        }
        List<Code> codes = codeService.getCodes(gameId);
        return new HttpEntity<>(codes);
    }

    @RequestMapping(value = "/{codeId}",
        method = GET,
        produces = APPLICATION_JSON_VALUE)
    public HttpEntity<Code> getCode(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("gameId") String gameId, @PathVariable("codeId") String code) {
        String userIdFromToken = tokenService.getUserIdFromToken(token);
        if (gameService.getGame(userIdFromToken, gameId) == null) {
            throw new AccessForbiddenException();
        }
        Code retrieved = codeService.getCode(gameId, code);
        if (retrieved == null) {
            throw new CodeNotFoundException();
        }
        return new HttpEntity<>(retrieved);
    }
}
