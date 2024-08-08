package edu.utsa.css3443.prepped;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ViewEventActivity extends AppCompatActivity {
    private TextView eventNameTextView;
    private TextView eventDateTextView;
    private TextView eventDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        eventNameTextView = findViewById(R.id.eventNameTextView);
        eventDateTextView = findViewById(R.id.eventDateTextView);
        eventDescriptionTextView = findViewById(R.id.eventDescriptionTextView);

        if (getIntent() != null && getIntent().getExtras() != null) {
            String eventName = getIntent().getStringExtra("eventName");
            String eventDateStr = getIntent().getStringExtra("eventDate");
            String eventDescription = getIntent().getStringExtra("eventDescription");

            eventNameTextView.setText(eventName);
            eventDateTextView.setText(eventDateStr);
            eventDescriptionTextView.setText(eventDescription);
        } else {

            try {
                List<Event> events = readEventsFromFile();
                if (!events.isEmpty()) {
                    Event event = events.get(0);
                    eventNameTextView.setText(event.getEventName());
                    eventDateTextView.setText(event.getEventDate().toString());
                    eventDescriptionTextView.setText(event.getDescription());
                } else {
                    eventNameTextView.setText("No events found");
                    eventDateTextView.setText("");
                    eventDescriptionTextView.setText("");
                }
            } catch (IOException e) {
                e.printStackTrace();
                eventNameTextView.setText("Error loading events");
                eventDateTextView.setText("");
                eventDescriptionTextView.setText("");
            }
        }
    }

    private List<Event> readEventsFromFile() throws IOException {
        List<Event> events = new ArrayList<>();
        File file = new File(getFilesDir(), "events.csv");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");
            Event event = new Event(data[0], data[1], LocalDate.parse(data[2]), data[3]);
            events.add(event);
        }
        bufferedReader.close();
        return events;
    }
}
