package com.sutpc.security.core.authentication.exception;


import org.springframework.security.core.AuthenticationException;

public class KeyCloakTokenException extends AuthenticationException {
    public KeyCloakTokenException(String msg) {
        super(msg);
    }
}
