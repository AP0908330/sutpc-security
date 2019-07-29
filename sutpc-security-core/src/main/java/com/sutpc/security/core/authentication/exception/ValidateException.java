package com.sutpc.security.core.authentication.exception;


import org.springframework.security.core.AuthenticationException;

public class ValidateException extends AuthenticationException {
    public ValidateException(String msg) {
        super(msg);
    }
}
