package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private CodesResourceAssembler assembler;

    @Autowired
    public CodeController(CodeService service, CodesResourceAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @RequestMapping(value = "/list",
            method = GET,
            produces = APPLICATION_JSON_VALUE)
    public HttpEntity<PagedResources<CodeResource>> list(@PathVariable("gameId") String game, @RequestParam(value = "page", required = false) Integer pageNumber) {
        Integer actualPageNumber = pageNumber == null ? 0 : pageNumber;
        Page<Code> codes = service.getCodes(game, actualPageNumber);

        List<CodeResource> codeResources = assembler.toResources(codes.getContent());
        PagedResources.PageMetadata pageMetadata = new PagedResources.PageMetadata(codes.getNumberOfElements(), codes.getNumber(), codes.getTotalElements(), codes.getTotalPages());
        PagedResources<CodeResource> pagedResources = new PagedResources<CodeResource>(codeResources, pageMetadata);
        return new HttpEntity<>(pagedResources);
    }

    @RequestMapping(value = "/{codeId}",
            method = GET,
            produces = APPLICATION_JSON_VALUE)
    public HttpEntity<CodeResource> getCode(@PathVariable("gameId") String game, @PathVariable("codeId") String code) {
        Code retrieved = service.getCode(game, code);
        if (retrieved == null) {
            throw new CodeNotFoundException();
        }
        CodeResource codeResource = assembler.toResource(retrieved);
        return new HttpEntity<>(codeResource);
    }
}
