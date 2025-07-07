package com.shop.ecommerce.exception;

public class DuplicateSlugException extends RuntimeException{
    public DuplicateSlugException() {
    }

    public DuplicateSlugException(String message) {
        super(message);
    }

    public DuplicateSlugException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateSlugException(Throwable cause) {
        super(cause);
    }

    public DuplicateSlugException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
