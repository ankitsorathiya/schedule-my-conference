/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schedulemyconference.conference.model;

import com.schedulemyconference.conference.validator.TimeValidator;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author dad's gift
 */
public class VariableEvent extends Event implements Cloneable {

    private final Calendar minStartTime;
    private final Calendar maxStartTime;
    private VariableEvent(Calendar startTime, Calendar endTime, EventType eventType, String eventDescription) {
        super(startTime, endTime, eventType, eventDescription);
        this.minStartTime = startTime;
        this.maxStartTime = endTime;
    }
    public VariableEvent(Calendar minStartTime, Calendar maxStartTime,String eventDescription) {
        this(minStartTime,maxStartTime, EventType.VARIABLE_EVENT, eventDescription);
    }

    public Calendar getMinStartTime() {
        return minStartTime;
    }

    public Calendar getMaxStartTime() {
        return maxStartTime;
    }

    @Override
    public String toString() {
        return TimeValidator.formatTime(getMaxStartTime().getTime()) + " " + getEventDescription() + "\n";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.minStartTime);
        hash = 79 * hash + Objects.hashCode(this.maxStartTime);
        hash = 79 * hash + Objects.hashCode(getEventDescription());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VariableEvent other = (VariableEvent) obj;
        if (!Objects.equals(this.minStartTime, other.minStartTime)) {
            return false;
        }
        if (!Objects.equals(this.maxStartTime, other.maxStartTime)) {
            return false;
        }
        if (!Objects.equals(getEventDescription(), other.getEventDescription())) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        VariableEvent clonedVariableEvent= new VariableEvent(minStartTime, maxStartTime,getEventDescription());
        return clonedVariableEvent;
    }

    public boolean isEventEndsBetweenMinStartAndMaxStart(Event event) {
         return event.getEnd().compareTo(minStartTime)>=0 && event.getEnd().compareTo(maxStartTime)<=0;
    }
}
