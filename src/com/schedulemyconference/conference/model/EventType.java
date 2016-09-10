/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schedulemyconference.conference.model;

import com.schedulemyconference.conference.exception.InvalidEventTypeException;

/**
 *
 * @author dad's gift
 */
public enum EventType {
    TALK("talk"),SESSION("session"), BREAK("break"), SURPRISE_EVENT("surpriseEvent"),VARIABLE_EVENT("networkingEvent");
    String eventType;
    EventType(String eventType) {
        this.eventType = eventType;
    }
    
    public static EventType getEventType(String eventName) throws InvalidEventTypeException {
        EventType[] eventTypes=values();
        for (EventType et : eventTypes) {
            if(et.getValue().equals(eventName)){
                return et;
            }
        }
        throw new InvalidEventTypeException(eventName+" -No such event exists!!");
    }
    public String getValue(){
        return eventType;
    }
}
