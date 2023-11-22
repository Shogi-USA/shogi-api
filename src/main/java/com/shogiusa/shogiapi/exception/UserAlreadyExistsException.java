package com.shogiusa.shogiapi.exception;

/**
 * Exception thrown when an attempt is made to create a user
 * that already exists in the system.
 * <p>
 * This class extends {@link RuntimeException} and is used
 * within the application to indicate a conflict in user creation
 * due to the existence of a user with similar credentials (e.g., email or username).
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     * The message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     *
     * @param message the detail message which is saved for later retrieval by the
     *                {@link Throwable#getMessage()} method.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}