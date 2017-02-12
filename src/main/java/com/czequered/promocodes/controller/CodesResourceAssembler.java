package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Code;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Martin Varga
 */
@Component
public class CodesResourceAssembler extends ResourceAssemblerSupport<Code, CodeResource> {

    public CodesResourceAssembler() {
        super(CodeController.class, CodeResource.class);
    }

    @Override
    public CodeResource toResource(Code code) {
        CodeResource resource = new CodeResource(code);
        resource.add(new Link("/api/v1/games/" + code.getGameId() + "/codes/" + code.getCodeId()));
        return resource;
    }
}