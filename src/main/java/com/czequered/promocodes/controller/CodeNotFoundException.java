package com.czequered.promocodes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Martin Varga
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such game or code.")
public class CodeNotFoundException extends RuntimeException {
}
