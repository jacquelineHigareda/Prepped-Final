package edu.utsa.css3443.prepped;

import android.content.Context;
import java.io.IOException;
import java.util.List;

public class HomeController {
    public static void initializeApp(Context context) throws IOException {
        List<Group> groups = GroupController.getAllGroups(context);
        List<Event> events = EventController.getAllEvents(context);
        assignEventsToGroups(groups, events);
    }

    public static void assignEventsToGroups(List<Group> groups, List<Event> events) {
        for (Group group : groups) {
            for (Event event : events) {
                if (event.getEventId().equals(group.getGroupId())) {
                    group.addEvent(event);
                }
            }
        }
    }
}
