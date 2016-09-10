package com.schedulemyconference.conference.scheduler;

import com.schedulemyconference.conference.model.Break;
import com.schedulemyconference.conference.model.Event;
import com.schedulemyconference.conference.model.EventType;
import com.schedulemyconference.conference.model.SurpriseEvent;
import com.schedulemyconference.conference.model.Track;
import com.schedulemyconference.conference.model.Session;
import com.schedulemyconference.conference.model.Talk;
import com.schedulemyconference.conference.model.TalkTiming;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Ankit Sorathiya
 * <p>
 * This class is useful to get possible timings of talk</p>
 */
public class TimeFrameGenerator {

    public List<Event> getAvailableTimings(List<Talk> talks, Track track) {
        BitSet availableMinutes = computeAvailableSlots(track);
        List<Event> talkTimingse = computeTimeFrameForTalks(availableMinutes, talks, track.getFirstEvent());
        return talkTimingse;
    }

    private BitSet computeAvailableSlots(Track track) {
        int begin = ((track.getFirstEvent().getStart().get(Calendar.HOUR_OF_DAY) * 60) + track.getFirstEvent().getStart().get(Calendar.MINUTE));
        int end = (track.getLastEvent().getEnd().get(Calendar.HOUR_OF_DAY) * 60) + track.getLastEvent().getEnd().get(Calendar.MINUTE);
        int totalMinutes = end - begin;// total minutes of conference, including break
        //bitset will hold available minutes to find time frame
        BitSet availableMinutes = new BitSet(totalMinutes);
        int fillStartPosition = 0;
        for (Event event : track.getEvents()) {
            int startMinutes = ((event.getStart().get(Calendar.HOUR_OF_DAY) * 60) + event.getStart().get(Calendar.MINUTE));
            int endMinutes = ((event.getEnd().get(Calendar.HOUR_OF_DAY) * 60) + event.getEnd().get(Calendar.MINUTE));
            int duration = fillStartPosition + (endMinutes - startMinutes);
            if (event instanceof Session) {
                // awesome devs are going to talk
                availableMinutes.set(fillStartPosition, duration, true);
            } else if (event instanceof SurpriseEvent || event instanceof Break) {
                // listner are going to enjoy some kind of refreshment.
                availableMinutes.set(fillStartPosition, duration, false);
            }
            fillStartPosition = duration;
        }
        return availableMinutes;
    }

    private List<Event> computeTimeFrameForTalks(BitSet emptySlots, List<Talk> talks, Event firstEvent) {
        List<Event> talkTimingse = new ArrayList<>();
        Calendar talkStart = (Calendar) firstEvent.getStart().clone();
        int startPosition = 0;
        Collections.sort(talks);
        while (emptySlots.nextSetBit(startPosition) > -1) {
            for (Talk talk : talks) {
                // is there a gap between next set bit and current set bit?
                int diff = emptySlots.nextSetBit(startPosition) - startPosition;
                if (diff > 1) {
                    //We have concrete proof that there a gap!!
                    //to maintain the calendar timing according to available minutes
                    //add the minute difference to calendar.
                    talkStart.add(Calendar.MINUTE, diff);
                }
                startPosition = emptySlots.nextSetBit(startPosition);// get next starting position
                int endPosition = startPosition + talk.getTalkDuration();// current position+ talk duration
                BitSet minutes = emptySlots.get(startPosition, endPosition); // starting postion to end postion
                if (minutes.cardinality() == talk.getTalkDuration()) {// is every minute free?
                    Calendar talkEnd = (Calendar) talkStart.clone();// clone existing one to calculate end time
                    talkEnd.add(Calendar.MINUTE, talk.getTalkDuration());// add minutes to it to get end time
                    talkTimingse.add(new TalkTiming(talk, (Calendar) talkStart.clone(), talkEnd, EventType.TALK)); // finally add it to list of timings
                }
            }
            // increase start postion and calendar minutes by 5
            // we can also increase it by one but here the talk time is of round number
            talkStart.add(Calendar.MINUTE, 5);
            startPosition = startPosition + 5;
        }
        return talkTimingse;
    }
}
