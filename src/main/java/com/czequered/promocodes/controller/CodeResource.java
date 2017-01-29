package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Code;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * @author Martin Varga
 */
public class CodeResource extends Resource<Code> {
    public CodeResource(Code code, Link... links) {
        super(code, links);
    }
}
