package com.schedulemyconference.conference.model;

import com.schedulemyconference.conference.model.Event;
import com.schedulemyconference.conference.model.EventType;
import com.schedulemyconference.conference.validator.TimeValidator;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author Ankit Sorathiya
 */
public class TalkTiming extends Event implements Cloneable {

    private final Talk talk;// holds talkinfo

    public TalkTiming(Talk talk, Calendar startTime, Calendar endTime, EventType eventType) {
        super(startTime, endTime, eventType, talk.getTalkTitle());
        this.talk = talk;
    }

    public Talk getTalk() {
        return this.talk;
    }

    @Override
    public String toString() {
        StringBuilder ouputBuilder = new StringBuilder();
        ouputBuilder.append(TimeValidator.formatTime(getStart().getTime())).append(" ").append(getTalk()).append("\n");
        return ouputBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TalkTiming other = (TalkTiming) obj;
        if (!Objects.equals(this.talk, other.talk)) {
            return false;
        }
        if (!Objects.equals(this.getStart(), other.getStart())) {
            return false;
        }
        if (!Objects.equals(this.getEnd(), other.getEnd())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + talk.hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        TalkTiming talkTiming = new TalkTiming(getTalk(), getStart(), getEnd(), getEventType());
        return talkTiming;
    }

}
