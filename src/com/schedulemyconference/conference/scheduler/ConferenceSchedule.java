package com.schedulemyconference.conference.scheduler;

import com.schedulemyconference.conference.model.Conference;
import com.schedulemyconference.conference.model.Track;
import com.schedulemyconference.conference.model.Talk;
import java.util.List;

public interface ConferenceSchedule {

    public Conference scheduleConference(List<Talk> talks,Track track) throws CloneNotSupportedException;
}
