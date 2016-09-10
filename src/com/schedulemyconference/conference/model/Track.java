package com.schedulemyconference.conference.model;

import com.schedulemyconference.conference.model.Event;
import com.schedulemyconference.conference.model.EventType;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Ankit Sorathiya
 * <p>
 * track will have morning and afternoon session with lunch and networking event
 * </p>
 */
public class Track implements Cloneable {

    private final SortedSet<Event> events;// can have sessions,break or networking event

    public Track(SortedSet<Event> events) {
        this.events = events;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.events);
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
        final Track other = (Track) obj;
        if (!Objects.equals(this.events, other.events)) {
            return false;
        }
        return true;
    }

    public Event getFirstEvent() {
        return getEvents().first();
    }

    public Event getLastEvent() {
        return getEvents().last();
    }

    public SortedSet<Event> getEvents() {
        return events;
    }

    public SortedSet<Event> filterEventsByEventType(EventType eventType, Event eventObj) {
        SortedSet<Event> filterdEvents = new TreeSet<>();
        for (Event event : getEvents()) {
            if (event.getEventType().equals(eventType) && eventObj.endsBeforeOrEquals(event.getEnd()) && eventObj.startsAfterOrEquals(event.getStart())) {
                filterdEvents.add(event);
            }
        }
        return filterdEvents;
    }

    public boolean fillEvent(EventType destination, Event event) {
        if (destination.equals(EventType.BREAK) || destination.equals(EventType.SURPRISE_EVENT)) {
            return false; // nothing can be filled in these types of event
        } else if (destination.equals(EventType.SESSION) && event.getEventType().equals(EventType.TALK)) {
            SortedSet<Event> filterdEvents = filterEventsByEventType(destination, event);
            if (!eventExists(filterdEvents, event)) {
                for (Event eventObj : filterdEvents) {
                    Session session = (Session) eventObj;
                    boolean result = session.addTalkTiming((Event) event);
                    if (result) {
                        return true; // added successfully into the destionation event type
                    }
                }
            }
        }
        return false;
    }

    private boolean eventExists(SortedSet<Event> events, Event event) {
        for (Event eventObj : events) {
            if (eventObj.hasSubEvent(event)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValid() throws NullPointerException {
        for (Event event : getEvents()) {
            if (event instanceof Session) {
                Session session = (Session) event;
                if (!session.isFilledCompletely()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder outputBuilder = new StringBuilder();
        for (Event event : events) {
            outputBuilder.append(event);
        }
        return outputBuilder.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SortedSet<Event> clonedEvents = new TreeSet<>();
        for (Event event : events) {
            Event clonedEvent = (Event) event.clone();
            clonedEvents.add(clonedEvent);
        }
        Track track = new Track(clonedEvents);
        return track;

    }

}
