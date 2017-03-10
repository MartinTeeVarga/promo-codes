package com.czequered.promocodes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Martin Varga
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request.")
public class InvalidRequestException extends RuntimeException {
}
