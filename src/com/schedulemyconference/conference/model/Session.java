package com.schedulemyconference.conference.model;

import com.schedulemyconference.conference.model.Event;
import com.schedulemyconference.conference.model.EventType;
import com.schedulemyconference.conference.model.VariableEvent;
import java.util.Calendar;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Ankit Sorathiya
 */
public class Session extends Event implements Cloneable {

    private final SortedSet<Event> talkTimings; // stores multiple talk
    private VariableEvent variableEvent = null;

    public Session(Calendar startTime, Calendar endTime, String eventDescription) {
        super(startTime, endTime, EventType.SESSION, eventDescription);
        talkTimings = new TreeSet<>();
    }

    public Session(Calendar startTime, Calendar endTime, String eventDescription, VariableEvent networkingEvent) {
        this(startTime, endTime, eventDescription);
        this.variableEvent = networkingEvent;
    }

    public void setVariableEvent(VariableEvent variableEvent) {
        this.variableEvent = variableEvent;
    }

    @Override
    public SortedSet<Event> getSubEvents() {
        return talkTimings;
    }

    /**
     *
     * @param other talk to be compared
     * @return true if current session holds the same talk
     */
    @Override
    public boolean hasSubEvent(Event other) {
        if (other instanceof TalkTiming == false) {
            return false;
        }
        TalkTiming otherTalkTiming = (TalkTiming) other;
        for (Event talkTiming : getSubEvents()) {
            if (((TalkTiming) talkTiming).getTalk().equals(otherTalkTiming.getTalk())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder outputBuilder = new StringBuilder();
        for (Event talkTiming : getSubEvents()) {
            outputBuilder.append(talkTiming);
        }
        return outputBuilder.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.talkTimings);
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
        final Session other = (Session) obj;
        if (!Objects.equals(this.talkTimings, other.talkTimings)) {
            return false;
        }
        return true;
    }

    @Override
    public Calendar getEnd() {
        if (variableEvent != null) {
            return variableEvent.getMaxStartTime();
        }
        return super.getEnd();
    }

    public VariableEvent getVariableEvent() {
        return variableEvent;
    }

    public boolean addTalkTiming(Event talkTiming) {
        if (doesFallInSessionTimespan(talkTiming)) {
            if (isFirstSubEvent(talkTiming)
                    || (!isSessionEmpty() && !isThereAGapBetweenTalks(talkTimings.last(), talkTiming) && talkTiming.endsBeforeOrEquals(getEnd()))) {
                return getSubEvents().add(talkTiming);
            }
        }
        return false;
    }

    public boolean doesFallInSessionTimespan(Event event) {
        return event.getStart().compareTo(getStart()) >= 0 && event.getEnd().compareTo(getEnd()) <= 0;
    }

    private boolean isThereAGapBetweenTalks(Event first, Event second) {
        return !first.getEnd().equals(second.getStart());
    }

    private boolean isFirstSubEvent(Event event) {
        return isSessionEmpty() && event.getStart().equals(getStart());
    }

    private boolean isSessionEmpty() {
        return getSubEvents().isEmpty();
    }

    public boolean isFilledCompletely() {
        if (!isSessionEmpty()) {
            if (isVariableEventEnd()) {
                return variableEvent.isEventEndsBetweenMinStartAndMaxStart(getSubEvents().last());
            } else {
                return getSubEvents().last().getEnd().equals(getEnd());
            }
        }
        return false;
    }

    private boolean isVariableEventEnd() {
        return variableEvent != null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Session session = new Session(getStart(), getEnd(), getEventDescription(), getVariableEvent());
        return session;
    }

}
