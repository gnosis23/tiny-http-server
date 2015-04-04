package org.bohao.exception;

/**
 * Created by bohao on 04-04-0004.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
