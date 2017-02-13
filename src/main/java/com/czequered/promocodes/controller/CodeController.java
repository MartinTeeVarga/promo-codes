package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Martin Varga
 */
@RestController
@RequestMapping("/api/v1/games/{gameId}/codes")
public class CodeController {

    private CodeService service;

    @Autowired
    public CodeController(CodeService service) {
        this.service = service;
    }

    @RequestMapping(value = "/list",
            method = GET,
            produces = APPLICATION_JSON_VALUE)
    public HttpEntity<List<Code>> list(@PathVariable("gameId") String game) {
        List<Code> codes = service.getCodes(game);

        return new HttpEntity<>(codes);
    }

    @RequestMapping(value = "/{codeId}",
            method = GET,
            produces = APPLICATION_JSON_VALUE)
    public HttpEntity<Code> getCode(@PathVariable("gameId") String game, @PathVariable("codeId") String code) {
        Code retrieved = service.getCode(game, code);
        if (retrieved == null) {
            throw new CodeNotFoundException();
        }
        return new HttpEntity<>(retrieved);
    }
}
