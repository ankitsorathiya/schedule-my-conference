# Shedule-my-conference
##### Problem Definition
We often come across a situation where in we choose to schdule many events in a day but not sure which order should they have?
This application can be useful for multiple events scheduling.
Every event has certian amount of time assigned, which could be 5 minutes or 10 minutes or may be an hour.
What this program does is it schedules such multiple events without having overlap of time and gives you multiple options to choose from.

##### How this can help you?
Let's take an example of conference scheduling where in organizer want to assign time slot to each event but not sure which order to assign and which should be conducted first and second and so on. That's where this program come at rescue.
> Example

- Below are the time slots in which you want to schedule your events.

| Start Time  | End Time| Type of event  | Subtype of event |
| ------------- | ------------- | ------------- | ------------- |
| 09:00 am | 12:00 pm  |session | Morning Session | 
| 12:00 pm | 01:00 pm | break | Lunch |
| 01:00 pm | 03:00 pm | session | Afternoon Session 1 |
| 03:00 pm | 03:15 pm | break | Refreshment break |
| 03:15 pm | 04:00 pm | session | Afternoon Session 2 |
| 04:00 pm | 05:00 pm | networkingEvent | Networking Event |

- Below are the events you have which you want to schedule in such a way that maximum talks can happen.

| Event Name  | minutes |
| ------------- | ------------- |
| Writing Fast Tests Against Enterprise Rails | 60min |
| Overdoing it in Python | 45min| 
| Lua for the Masses | 30min | 
| Ruby Errors from Mismatched Gem Versions | 45min | 
| Common Ruby Errors | 45min | 
| Rails Magic | 60min |
| Ruby on Rails: Why We Should Move On | 60min |
| Clojure Ate Scala (on my project) | 45min |
| Programming in the Boondocks of Seattle | 30min |
| Ruby vs. Clojure for Back-End Development | 30min |
| Ruby on Rails Legacy App Maintenance | 60min |
| A World Without HackerNews | 30min |
| User Interface CSS in Rails Apps | 30min |

> Note 
- Input and output files
  - [Available time format](src/com/schedulemyconference/resources/conferenceEventInput.txt) 
  - [Events to schedule](src/com/schedulemyconference/resources/talkInput.txt)
  - [Output](src/com/schedulemyconference/resources/conferenceOutput.txt)


# Programming Language
Java, version 1.7
