package edu.utsa.css3443.prepped;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CreateEventActivity extends AppCompatActivity {
    private EditText eventNameEditText;
    private EditText eventDateEditText;
    private EditText eventDescriptionEditText;
    private Spinner groupSpinner;
    private Button createEventButton;
    private List<Group> groups;

    private static final String TAG = "CreateEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventNameEditText = findViewById(R.id.eventNameEditText);
        eventDateEditText = findViewById(R.id.eventDateEditText);
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText);
        groupSpinner = findViewById(R.id.groupSpinner);
        createEventButton = findViewById(R.id.createEventButton);

        // Load groups
        try {
            groups = GroupController.getAllGroups(this);
            ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            groupSpinner.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load groups", Toast.LENGTH_SHORT).show();
        }

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Create Event Button Clicked");
                createEvent();
            }
        });
    }

    private void createEvent() {
        Log.d(TAG, "createEvent() called");
        String eventName = eventNameEditText.getText().toString().trim();
        String eventDate = eventDateEditText.getText().toString().trim();
        String eventDescription = eventDescriptionEditText.getText().toString().trim();
        Group selectedGroup = (Group) groupSpinner.getSelectedItem();

        Log.d(TAG, "Event Name: " + eventName);
        Log.d(TAG, "Event Date: " + eventDate);
        Log.d(TAG, "Event Description: " + eventDescription);
        Log.d(TAG, "Selected Group: " + (selectedGroup != null ? selectedGroup.getGroupName() : "null"));

        if (!eventName.isEmpty() && !eventDate.isEmpty() && selectedGroup != null) {
            try {
                LocalDate date = LocalDate.parse(eventDate);
                String eventId = String.valueOf(System.currentTimeMillis()); // Generate eventId
                Event newEvent = new Event(eventId, eventName, date, eventDescription);
                EventController.addEvent(CreateEventActivity.this, newEvent);

                Toast.makeText(CreateEventActivity.this, "Event created", Toast.LENGTH_SHORT).show();
                finish();
            } catch (DateTimeParseException e) {
                Toast.makeText(CreateEventActivity.this, "Invalid date format", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(CreateEventActivity.this, "Failed to create event", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CreateEventActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
