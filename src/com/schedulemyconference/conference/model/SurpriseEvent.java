package com.schedulemyconference.conference.model;

import java.util.Calendar;

/**
 *
 * @author Ankit Sorathiya
 */
public class SurpriseEvent extends Event {

    public SurpriseEvent(Calendar startTime, Calendar endTime,String eventDescription) {
        super(startTime, endTime,EventType.SURPRISE_EVENT,eventDescription);
    }
}
