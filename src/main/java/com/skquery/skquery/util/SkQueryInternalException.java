package com.skquery.skquery.util;

@SuppressWarnings("serial")
public class SkQueryInternalException extends RuntimeException {

    public SkQueryInternalException() {
        super();
    }

    public SkQueryInternalException(String message) {
        super(message);
    }

    public SkQueryInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkQueryInternalException(Throwable cause) {
        super(cause);
    }
}
