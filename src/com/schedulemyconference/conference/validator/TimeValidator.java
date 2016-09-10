package com.schedulemyconference.conference.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ankit Sorathiya
 */
public class TimeValidator {
    private static final DateFormat TIMEFORMAT = new SimpleDateFormat("hh:mm a");
    /**
     * 
     * @param dateTime date time to be formated.
     * @return formatted date time in hh:mm a format
     */
    public static String formatTime(Date dateTime) {
        return TIMEFORMAT.format(dateTime);
    }
    /**
     * 
     * @param dateString  date string to be parsed
     * @return parsed date
     * @throws ParseException 
     */
    public static Date parse(String dateString) throws ParseException {
        return TIMEFORMAT.parse(dateString);
    }
}
