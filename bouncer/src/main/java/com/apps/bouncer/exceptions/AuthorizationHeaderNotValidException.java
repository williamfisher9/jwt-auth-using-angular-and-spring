package com.apps.bouncer.exceptions;

public class AuthorizationHeaderNotValidException extends RuntimeException {
    public AuthorizationHeaderNotValidException(String message) {
        super(message);
    }
}
