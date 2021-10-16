package com.bookstore.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;


import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {

    public AuthorizationException(String message) {
        super(message);
    }
}
