package com.yondu.university.project_rohan.exception;

public class ParameterException extends RuntimeException {

    /**
     * @param message
     */
    public ParameterException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

}
