package com.schedulemyconference.conference.exception;

/**
 * @author Ankit Sorathiya
 */
public class InvalidEventTimeException extends UnsupportedOperationException {

    /**
     * <p>
     * Throw this exception when conference detail is invalid example<b>
     * conference start time clashes with lunch time</b>
     * </p>
     *
     * @param message exception message
     */
    public InvalidEventTimeException(String message) {
        super(message);
    }
}
