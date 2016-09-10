package com.schedulemyconference.conference.factory;

import com.schedulemyconference.conference.exception.InvalidTalkException;
import com.schedulemyconference.conference.model.Talk;
import com.schedulemyconference.conference.model.TalkDuration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ankit Sorathiya It has methods to create Talk from the given raw
 * input
 */
public class TalkFactory {

    private static TalkFactory talkFactory;

    private TalkFactory() {

    }

    public static TalkFactory getInstance() {
        if (talkFactory == null) {
            talkFactory = new TalkFactory();
        }
        return talkFactory;
    }

    /**
     *
     * @param filePath input file path
     * @return list of Talk
     * @throws IOException
     * @throws InvalidTalkException
     * @see InvalidTalkException
     * @see IOException
     */
    public List<Talk> buildTalks(String filePath) throws IOException, InvalidTalkException {
        List<Talk> talks = new ArrayList<>();
        File inputFile = new File(filePath);
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(inputFile))) {
            String talkDescription; // talk title and duration
            while ((talkDescription = bufferReader.readLine()) != null) {
                talkDescription = talkDescription.trim();
                if (talkDescription.length() > 0) {
                    talks.add(buildTalk(talkDescription));// add talk
                }
            }
        }
        return talks;
    }

    /**
     *
     * @param talkInfo contains talk title and duration ,example write program
     * using java 20min
     * @return Talk
     * @throws InvalidTalkException
     */
    private Talk buildTalk(String talkInfo) throws InvalidTalkException {
        int index = talkInfo.lastIndexOf(" "); // get the index of last space
        if (index > -1) {
            String title = talkInfo.substring(0, index); // get title part
            String duration = talkInfo.substring(index, talkInfo.length()); // get duration part
            return new Talk(title, getTalkDuration(duration));
        }
        throw new InvalidTalkException("Invalid talk title :" + talkInfo);
    }

    /**
     *
     * @param duration duration string eg. 60min,10min or lightning which
     * represents 5 min
     * @return duration in minutes
     */
    private int getTalkDuration(String duration) throws InvalidTalkException {
        int durationInMinute = 0;
        try {
            if (duration != null) {
                duration = duration.trim().toLowerCase();// convert to lowercase
                if (TalkDuration.LIGHTNING.equals(duration.trim())) {// if it is lighning
                    durationInMinute = TalkDuration.LIGHTNING_DURATION;// return lighting minute
                } else {
                    if (duration.contains("min")) {
                        durationInMinute = Integer.parseInt(duration.substring(0, duration.indexOf("min")));
                    }
                }
            }
        } catch (NumberFormatException e) {
            throw new InvalidTalkException("Invalid talk duration :" + duration + " \n " + e.getMessage());
        }
        return durationInMinute;
    }
}
