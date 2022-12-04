package com.yondu.university.project_rohan.exception;

public class ResourceNotFoundException extends RuntimeException {

    /**
     * @param message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
