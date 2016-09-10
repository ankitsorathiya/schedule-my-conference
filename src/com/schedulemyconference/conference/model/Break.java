/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schedulemyconference.conference.model;

import java.util.Calendar;

/**
 *
 * @author Ankit Sorathiya It holds lunch start and end time.
 */
public class Break extends Event {
    public Break(Calendar startTime, Calendar endTime,String eventDescription) {
        super(startTime, endTime,EventType.BREAK,eventDescription);
    }

}
