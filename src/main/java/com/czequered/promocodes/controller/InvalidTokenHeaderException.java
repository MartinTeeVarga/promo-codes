package com.czequered.promocodes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Martin Varga
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Invalid token.")
public class InvalidTokenHeaderException extends RuntimeException {

}
