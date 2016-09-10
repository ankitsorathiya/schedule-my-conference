package com.schedulemyconference.conference.fileoperation;

import com.schedulemyconference.conference.model.Conference;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ankit Sorathiya
 * <p>
 * This class is useful to write conference scheduling info to file
 * </p>
 */
public class ConferenceFileWriter {

    private final Conference conference;
    private final String filePath;

    public ConferenceFileWriter(String filePath, Conference conference) {
        this.filePath = filePath;
        this.conference = conference;
    }

    /**
     * Writes optimized conference talk info into file
     *
     * @throws IOException
     */
    public void writeConference() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile(); // if does not exists create one
        }
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile()); BufferedWriter bw = new BufferedWriter(fw)) {
            String output=conference.toString();
            bw.write(output);
            bw.flush();
        }
    }

}
