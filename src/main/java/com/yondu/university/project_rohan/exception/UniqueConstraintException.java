package com.yondu.university.project_rohan.exception;

public class UniqueConstraintException extends RuntimeException {

    /**
     * @param message
     */
    public UniqueConstraintException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public UniqueConstraintException(String message, Throwable cause) {
        super(message, cause);
    }
}
