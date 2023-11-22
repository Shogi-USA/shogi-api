package com.shogiusa.shogiapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that a requested user could not be found.
 * <p>
 * This class extends {@link RuntimeException} and is annotated with
 * {@link ResponseStatus} to automatically trigger a 404 Not Found response
 * when this exception is thrown. It is typically used in situations where
 * a user-related query (such as searching by ID or email) fails to find
 * the user in the system.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     * The message is used to provide a more specific error description which
     * can be retrieved later using {@link Throwable#getMessage()}.
     *
     * @param message the detail message, providing more context about the
     *                reason why the user was not found.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}