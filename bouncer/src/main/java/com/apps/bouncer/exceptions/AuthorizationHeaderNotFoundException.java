package com.apps.bouncer.exceptions;

public class AuthorizationHeaderNotFoundException extends RuntimeException {
    public AuthorizationHeaderNotFoundException(String message) {
        super(message);
    }
}
