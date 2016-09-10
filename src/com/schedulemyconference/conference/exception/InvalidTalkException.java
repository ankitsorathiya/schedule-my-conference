package com.schedulemyconference.conference.exception;

/**
 *
 * @author Ankit Sorathiya
 */
public class InvalidTalkException extends Exception {

    /**
     *
     * @param message  message of exception
     * If talk is invalid throw this exception
     */
    public InvalidTalkException(String message) {
        super(message);
    }

}
