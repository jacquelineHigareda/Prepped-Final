package edu.utsa.css3443.prepped;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventId;
    private String eventName;
    private LocalDate eventDate;
    private String description;

    public Event(String eventId, String eventName, LocalDate eventDate, String description) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.description = description;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void writeEventToCSV(Event event, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("%s,%s,%s,%s\n", event.eventId, event.eventName, event.eventDate.toString(), event.description);
        printWriter.close();
    }

    public static List<Event> readEventsFromCSV(String filePath) throws IOException {
        List<Event> events = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");
            Event event = new Event(data[0], data[1], LocalDate.parse(data[2]), data[3]);
            events.add(event);
        }
        bufferedReader.close();
        return events;
    }

    public static void deleteEventFromCSV(String eventId, String filePath) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File("temp.csv");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (!data[0].equals(eventId)) {
                writer.println(line);
            }
        }
        reader.close();
        writer.close();
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", description='" + description + '\'' +
                '}';
    }
}
