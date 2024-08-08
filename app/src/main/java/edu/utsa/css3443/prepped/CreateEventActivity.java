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

        loadGroups();

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });
    }

    private void loadGroups() {
        try {
            groups = GroupController.getAllGroups(this);
            if (groups != null && !groups.isEmpty()) {
                ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                groupSpinner.setAdapter(adapter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void createEvent() {
        String eventName = eventNameEditText.getText().toString().trim();
        String eventDate = eventDateEditText.getText().toString().trim();
        String eventDescription = eventDescriptionEditText.getText().toString().trim();
        Group selectedGroup = (Group) groupSpinner.getSelectedItem();
        String eventId = selectedGroup.getGroupName() + eventName;

        if (!eventName.isEmpty() && !eventDate.isEmpty() && selectedGroup != null) {
            try {
                LocalDate date = LocalDate.parse(eventDate);
                Event newEvent = new Event(eventId, eventName, date, eventDescription);
                EventController.addEvent(CreateEventActivity.this, newEvent);
                finish();
            }catch (IOException e) {
                e.printStackTrace();
            }
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
