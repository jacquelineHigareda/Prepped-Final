package edu.utsa.css3443.prepped;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static final String EXPIRED_EVENTS_FILE_PATH = "expired_events.csv";

    public static void handleExpiredEvents(List<Event> events, String eventsFilePath) throws IOException {
        LocalDate now = LocalDate.now();
        List<Event> expiredEvents = new ArrayList<>();

        for (Event event : events) {
            if (event.getEventDate().isBefore(now)) {
                expiredEvents.add(event);
            }
        }

        events.removeAll(expiredEvents);
        for (Event expiredEvent : expiredEvents) {
            Event.writeEventToCSV(expiredEvent, EXPIRED_EVENTS_FILE_PATH);
            Event.deleteEventFromCSV(expiredEvent.getEventId(), eventsFilePath);
        }
    }
}
