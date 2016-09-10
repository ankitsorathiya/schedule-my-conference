package com.schedulemyconference.conference.model;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Ankit Sorathiya
 */
public class Conference implements EventIterator {

    private final List<Track> tracks;//Conference has different possible tracks

    public Conference(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        StringBuilder outputBuilder = new StringBuilder("Conference Track Management:\n");
        for (int index = 0; index < tracks.size(); index++) {
            outputBuilder.append("Track ").append(index + 1).append("\n");
            outputBuilder.append(tracks.get(index));
        }
        return outputBuilder.toString();

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.tracks);
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
        final Conference other = (Conference) obj;
        if (!Objects.equals(this.tracks, other.tracks)) {
            return false;
        }
        return true;
    }

    @Override
    public Iterator getIterator() {
        return this.tracks.iterator();
    }

}
