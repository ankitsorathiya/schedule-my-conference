/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schedulemyconference.conference.factory;

import com.schedulemyconference.conference.exception.InvalidEventTimeException;
import com.schedulemyconference.conference.model.SurpriseEvent;
import com.schedulemyconference.conference.model.Event;
import com.schedulemyconference.conference.model.EventType;
import com.schedulemyconference.conference.model.VariableEvent;
import com.schedulemyconference.conference.model.Break;
import com.schedulemyconference.conference.validator.TimeValidator;
import com.schedulemyconference.conference.model.Session;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Ankit Sorathiya
 */
public class EventFactory {

    private static EventFactory eventFactory;

    public static EventFactory getInstance() {
        if (eventFactory == null) {
            eventFactory = new EventFactory();
        }
        return eventFactory;
    }

    private EventFactory() {
    }

    public Event buildEvent(String startTime, String endTime, EventType eventType, String eventDescription) throws ParseException {
        return buildEvent(startTime, endTime, eventType, eventDescription, null);
    }

    public Event buildEvent(String startTime, String endTime, EventType eventType, String eventDescription, VariableEvent variableEvent) throws ParseException {
        Calendar start = Calendar.getInstance();
        start.setTime(TimeValidator.parse(startTime));
        Calendar end = Calendar.getInstance();
        end.setTime(TimeValidator.parse(endTime));
        Event event = null;
        switch (eventType) {
            case SESSION:
                event = new Session(start, end, eventDescription, variableEvent);
                break;
            case BREAK:
                event = new Break(start, end, eventDescription);
                break;
            case SURPRISE_EVENT:
                event = new SurpriseEvent(start, end, eventDescription);
                break;
            case VARIABLE_EVENT:
                event = new VariableEvent(start, end, eventDescription);
                break;
        }
        return event;
    }

    public SortedSet<Event> buildEventsFromFile(String conferenceEventsTimeFilePath) throws InvalidEventTimeException, IOException, ParseException, NullPointerException {
        SortedSet<Event> events = new TreeSet<>();
        File inputFile = new File(conferenceEventsTimeFilePath);
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(inputFile))) {
            String eventDetail;
            while ((eventDetail = bufferReader.readLine()) != null) {
                eventDetail = eventDetail.trim();
                if (eventDetail.length() > 0) {
                    String[] eventDetailObj = eventDetail.split(",");
                    String startTime = eventDetailObj[0].trim();
                    String endTime = eventDetailObj[1].trim();
                    String eventName = eventDetailObj[2].trim();
                    String eventDescription = eventDetailObj[3].trim();
                    Event event = buildEvent(startTime, endTime, EventType.getEventType(eventName), eventDescription);
                    if (event.getEventType().equals(EventType.VARIABLE_EVENT) && !events.isEmpty()
                            && events.last().getEventType().equals(EventType.SESSION)) {
                        ((Session) events.last()).setVariableEvent((VariableEvent) event); // set variable end of session
                    } 
                    events.add(event);
                }
            }
        }
        return events;
    }
}
