/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schedulemyconference.conference.main;

import com.schedulemyconference.conference.model.Conference;
import com.schedulemyconference.conference.model.Event;
import com.schedulemyconference.conference.factory.EventFactory;
import com.schedulemyconference.conference.validator.EventValidator;
import com.schedulemyconference.conference.fileoperation.ConferenceFileWriter;
import com.schedulemyconference.conference.scheduler.ConferenceSchedule;
import com.schedulemyconference.conference.scheduler.ConferenceScheduler;
import com.schedulemyconference.conference.model.Track;
import com.schedulemyconference.conference.model.Talk;
import com.schedulemyconference.conference.factory.TalkFactory;
import com.schedulemyconference.conference.exception.InvalidTalkException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.List;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ankit Sorathiya
 */
public class ConferenceTrackMangement {

    public static void main(String[] args) {
        try {
            ConferenceTrackMangement conferenceTrackMangement = new ConferenceTrackMangement();
            String inputTalksFilePath = URLDecoder.decode(ConferenceTrackMangement.class.getResource("/com/thoughtworks/resources/talkInput.txt").getPath(), "utf-8");
            String outputConferenceFilePath = URLDecoder.decode(ConferenceTrackMangement.class.getResource("/com/thoughtworks/resources/conferenceOutput.txt").getPath(), "utf-8");
            String conferenceEventInputFilePath = URLDecoder.decode(ConferenceTrackMangement.class.getResource("/com/thoughtworks/resources/conferenceEventInput.txt").getPath(), "utf-8");
            conferenceTrackMangement.testOutMyConferenceScheduler(inputTalksFilePath, outputConferenceFilePath, conferenceEventInputFilePath);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConferenceTrackMangement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void testOutMyConferenceScheduler(String inputFilePath, String outputFilePath, String eventsDetailFilePath) {
        try {
            EventFactory eventFactory=EventFactory.getInstance();
            SortedSet<Event> events= eventFactory.buildEventsFromFile(eventsDetailFilePath);
            if (EventValidator.validateEvents(events)) {
                TalkFactory talkFactory = TalkFactory.getInstance();
                List<Talk> talks = talkFactory.buildTalks(inputFilePath);
                ConferenceSchedule conferenceSchedule = new ConferenceScheduler();
                Conference conference = conferenceSchedule.scheduleConference(talks, new Track(events));
                ConferenceFileWriter conferenceFileWriter = new ConferenceFileWriter(outputFilePath, conference);
                conferenceFileWriter.writeConference();
            }

        } catch (ParseException | IOException | InvalidTalkException | CloneNotSupportedException ex) {
            Logger.getLogger(ConferenceTrackMangement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
