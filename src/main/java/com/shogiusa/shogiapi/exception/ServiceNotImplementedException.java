package com.shogiusa.shogiapi.exception;

/**
 * Exception thrown when a service method is called that has not yet been implemented.
 * <p>
 * This class extends {@link RuntimeException} and is used within the application
 * to indicate that a service method has not yet been implemented.
 */
public class ServiceNotImplementedException extends RuntimeException {
    public ServiceNotImplementedException(String message) {
        super(message);
    }
}