package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Code;
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
    public CodeResource toResource(Code codes) {
        CodeResource resource = new CodeResource(codes);
        return resource;
    }
}