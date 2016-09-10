/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schedulemyconference.conference.validator;

import com.schedulemyconference.conference.exception.InvalidEventTimeException;
import com.schedulemyconference.conference.model.Event;
import com.schedulemyconference.conference.model.EventType;
import com.schedulemyconference.conference.validator.TimeValidator;
import java.util.SortedSet;

/**
 *
 * @author dad's gift
 */
public class EventValidator {

    /**
     *
     * @param events
     * @return false if events are not having proper time
     */
    public static boolean validateEvents(SortedSet<Event> events) throws InvalidEventTimeException {
        Event previousEvent = null;
        for (Event event : events) {
            if (previousEvent != null) {
                if (event.getEventType().equals(EventType.VARIABLE_EVENT)) {
                    // do nothing
                    //VariableEvent variableEvent=(VariableEvent) event;
                    //if(!(previousEvent.endsBeforeOrEquals(variableEvent.getMaxStartTime()) && variableEvent.startsAfterOrEquals(previousEvent.getEnd()))){
                    //throw new InvalidEventTimeException(variableEvent.getEventDescription()+ " is not set with propar time");
                    //}
                } else if (event.getStart().compareTo(previousEvent.getEnd()) < 0) {
                    throw new InvalidEventTimeException("Invalid event" + TimeValidator.formatTime(event.getStart().getTime()) + " Confilicts with " + TimeValidator.formatTime(previousEvent.getEnd().getTime()));
                }
            }
            if (event.getStart().compareTo(event.getEnd()) >= 0) {
                throw new InvalidEventTimeException("Invalid event, start time " + TimeValidator.formatTime(event.getStart().getTime()) + " should be less than " + TimeValidator.formatTime(event.getEnd().getTime()));
            }
            previousEvent = event;
        }
        return true;
    }
}
