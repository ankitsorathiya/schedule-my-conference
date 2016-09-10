/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schedulemyconference.conference.model;

import com.schedulemyconference.conference.validator.TimeValidator;
import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author dad's gift
 */
public abstract class Event implements Comparable<Event>, Cloneable {
    private final EventType eventType;
    private final Calendar start;
    private final Calendar end;
    private final String eventDescription;

    public Calendar getStart() {
        return start;
    }

    public Calendar getEnd() {
        return end;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public Event(Calendar startTime, Calendar endTime, EventType eventType, String eventDescription) {
        this.start = startTime;
        this.end = endTime;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
    }

    @Override
    public int compareTo(Event o) {
        return this.getStart().compareTo(o.getStart());
    }

    @Override
    public String toString() {
        return TimeValidator.formatTime(getStart().getTime()) + " " + getEventDescription() + "\n";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public SortedSet<Event> getSubEvents() {
        return new TreeSet<>(); // default implementation
    }
    public boolean hasSubEvent(Event event) {
        // default implementation
        return false;
    }


    public boolean startsAfterOrEquals(Calendar cal) {
        return getStart().compareTo(cal)>=0;
    }

    public boolean endsBeforeOrEquals(Calendar cal) {
        return getEnd().compareTo(cal)<=0;
    }
}
