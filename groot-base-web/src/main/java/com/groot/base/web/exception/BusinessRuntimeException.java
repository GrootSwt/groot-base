package com.groot.base.web.exception;

public class BusinessRuntimeException extends RuntimeException {

    public BusinessRuntimeException() {
        super();
    }

    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRuntimeException(Throwable cause) {
        super(cause);
    }
}
