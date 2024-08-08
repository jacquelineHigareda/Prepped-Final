package edu.utsa.css3443.prepped;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class EventController {
    public static void addEvent(Context context, Event event) throws IOException {
        File file = new File(context.getFilesDir(), "events.csv");
        Event.writeEventToCSV(event, file.getPath());
    }

    public static List<Event> getAllEvents(Context context) throws IOException {
        File file = new File(context.getFilesDir(), "events.csv");
        return Event.readEventsFromCSV(file.getPath());
    }

    public static void deleteEvent(Context context, String eventId) throws IOException {
        File file = new File(context.getFilesDir(), "events.csv");
        Event.deleteEventFromCSV(eventId, file.getPath());
    }
}
