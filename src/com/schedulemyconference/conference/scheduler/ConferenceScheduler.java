package com.schedulemyconference.conference.scheduler;

import com.schedulemyconference.conference.model.Track;
import com.schedulemyconference.conference.model.Conference;
import com.schedulemyconference.conference.model.Event;
import com.schedulemyconference.conference.model.EventType;
import com.schedulemyconference.conference.model.Talk;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ankit Sorathiya
 * <b>
 * Finds the best possible talks that can fit into different session of
 * conference.
 * </b>
 */
public class ConferenceScheduler implements ConferenceSchedule {

    @Override
    public Conference scheduleConference(List<Talk> talks, Track track) throws CloneNotSupportedException {
        List<Event> talkTimings = getTalkTimings(talks, track);
        List<Track> tracks = fillEmptyEventSlotsInTrack(talkTimings, track);
        return new Conference(tracks);
    }

    private List<Event> getTalkTimings(List<Talk> talks, Track track) {
        TimeFrameGenerator timeGenerator = new TimeFrameGenerator();
        List<Event> talkTimings = timeGenerator.getAvailableTimings(talks, track);
        return talkTimings;
    }

    private List<Track> fillEmptyEventSlotsInTrack(List<Event> talkTimings, Track track) throws CloneNotSupportedException {
        List<Track> tracks = new ArrayList<>();//Will store track, which satisfies rules of conference
        int currentIndex = 0;
        int totalTalkTimings = talkTimings.size();
        Event firstEvent = track.getFirstEvent();
        while (currentIndex < totalTalkTimings) {
            //move forward only if the current element starts exactly @ first event's start time
            if (firstEvent.getEventType().equals(EventType.SESSION)
                    && talkTimings.get(currentIndex).getStart().getTime().compareTo(firstEvent.getStart().getTime()) == 0) {
                int nextStartIndex = currentIndex + 1;
                int previousIndex = 0;
                while (nextStartIndex < totalTalkTimings) {
                    if (previousIndex == nextStartIndex) {
                        break; // no possible session exists anymore
                    }
                    previousIndex = nextStartIndex;
                    Track trackObj = (Track) track.clone();
                    trackObj.fillEvent(EventType.SESSION, talkTimings.get(currentIndex));
                    nextStartIndex = addOptimizedTalkTimingsToSession(nextStartIndex, talkTimings, trackObj);
                    if (trackObj.isValid()) {
                        tracks.add(trackObj);
                    }
                }
            } else {
                break; // first element does not start with the morning session start time then jump out.
            }
            currentIndex++;
        }
        return tracks;
    }

    private int addOptimizedTalkTimingsToSession(int startIndex, List<Event> talkTimings, Track track) {
        int nextStartIndex = startIndex;
        int totalTalks = talkTimings.size();
        boolean foundNextPossibleStartIndex = false;
        for (int index = startIndex; index < totalTalks; index++) {
            boolean filled = track.fillEvent(EventType.SESSION, talkTimings.get(index));
            if (filled && !foundNextPossibleStartIndex) {
                nextStartIndex = index + 1;
                foundNextPossibleStartIndex = true;
            }
        }
        return nextStartIndex;
    }
}
