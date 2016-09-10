package com.schedulemyconference.conference.model;

import java.util.Objects;

/**
 *
 * @author Ankit Sorathiya
 * <p>
 * Holds talk title and duration</p>
 */
public class Talk implements Comparable<Talk>{

    private final String talkTitle; // title of the talk 
    private final int talkDuration; // duration of the talk in minute

    public Talk(String talkTitle, int talkDuration) {
        this.talkTitle = talkTitle;
        this.talkDuration = talkDuration;
    }

    public String getTalkTitle() {
        return talkTitle;
    }

    public int getTalkDuration() {
        return talkDuration;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.talkTitle);
        hash = 79 * hash + this.talkDuration;
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
        final Talk other = (Talk) obj;
        if (!Objects.equals(this.talkTitle, other.talkTitle)) {
            return false;
        }
        if (this.talkDuration != other.talkDuration) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getTalkTitle() + " " + (getTalkDuration() == TalkDuration.LIGHTNING_DURATION ? TalkDuration.LIGHTNING : getTalkDuration() + "min");
    }

    @Override
    public int compareTo(Talk o) {
        return (o.getTalkDuration()-getTalkDuration());
}
}
