package com.jpmorgan.manager;

public class ResourceError extends Exception {
    public ResourceError(String message, Throwable throwable) {
        super(message, throwable);
    }
}
