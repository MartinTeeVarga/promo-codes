package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.service.CodeService;
import com.czequered.promocodes.service.GameService;
import com.czequered.promocodes.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Martin Varga
 */
@RestController
@RequestMapping("/api/v1/games/{gameId}/codes")
public class CodeController {
    Logger logger = LoggerFactory.getLogger(CodeController.class);

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
    public HttpEntity<List<Code>> list(@RequestHeader(name = TOKEN_HEADER) String token,
                                       @PathVariable("gameId") String gameId) {
        logger.debug("Listing all codes for '{}'", gameId);
        checkAccessRights(token, gameId);
        List<Code> codes = codeService.getCodes(gameId);
        return new HttpEntity<>(codes);
    }

    @RequestMapping(value = "/{codeId}",
            method = GET,
            produces = APPLICATION_JSON_VALUE)
    public HttpEntity<Code> getCode(@RequestHeader(name = TOKEN_HEADER) String token,
                                    @PathVariable("gameId") String gameId,
                                    @PathVariable("codeId") String code) {
        checkAccessRights(token, gameId);
        Code retrieved = codeService.getCode(gameId, code);
        if (retrieved == null) {
            throw new CodeNotFoundException();
        }
        return new HttpEntity<>(retrieved);
    }

    @RequestMapping(method = POST,
            produces = APPLICATION_JSON_VALUE)
    public HttpEntity<Code> saveNewCode(@RequestHeader(name = TOKEN_HEADER) String token,
                                        @RequestBody(required = true) Code code) {
        checkAccessRights(token, code.getGameId());
        // consider splitting the service save to create and update and move this logic there
        Code retrieved = codeService.getCode(code.getGameId(), code.getCodeId());
        if (retrieved != null) {
            throw new InvalidRequestException();
        }
        Code saved = codeService.saveCode(code);
        return new HttpEntity<>(saved);
    }

    @RequestMapping(method = PUT,
            produces = APPLICATION_JSON_VALUE)
    public HttpEntity<Code> saveExistingCode(@RequestHeader(name = TOKEN_HEADER) String token,
                                             @RequestBody(required = true) Code code) {
        checkAccessRights(token, code.getGameId());
        // consider splitting the service save to create and update and move this logic there
        Code retrieved = codeService.getCode(code.getGameId(), code.getCodeId());
        if (retrieved == null) {
            throw new InvalidRequestException();
        }
        Code saved = codeService.saveCode(code);
        return new HttpEntity<>(saved);
    }

    @RequestMapping(value = "/{codeId}",
            method = DELETE,
            produces = APPLICATION_JSON_VALUE)
    public HttpEntity deleteCode(@RequestHeader(name = TOKEN_HEADER) String token,
                                 @PathVariable("gameId") String gameId,
                                 @PathVariable("codeId") String codeId) {
        logger.debug("Deleting gameId '{}', codeId '{}'", gameId, codeId);
        checkAccessRights(token, gameId);
        codeService.deleteCode(gameId, codeId);
        return HttpEntity.EMPTY;
    }


    private void checkAccessRights(String token, String gameId) {
        String userIdFromToken = tokenService.getUserIdFromToken(token);
        if (gameService.getGame(userIdFromToken, gameId) == null) {
            logger.debug("Access forbidden to gameId '{}', for token: '{}'", gameId, token);
            throw new AccessForbiddenException();
        }
    }
}
