package edu.utsa.css3443.prepped;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

public class CreateGroupActivity extends AppCompatActivity {
    private EditText groupNameEditText;
    private EditText groupColorEditText;
    private EditText groupDescriptionEditText;
    private Button createGroupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupNameEditText = findViewById(R.id.groupNameEditText);
        groupColorEditText = findViewById(R.id.groupColorEditText);
        groupDescriptionEditText = findViewById(R.id.groupDescriptionEditText);
        createGroupButton = findViewById(R.id.createGroupButton);

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = groupNameEditText.getText().toString().trim();
                String groupColor = groupColorEditText.getText().toString().trim();
                String groupDescription = groupDescriptionEditText.getText().toString().trim();

                if (!groupName.isEmpty() && !groupColor.isEmpty() && !groupDescription.isEmpty()) {
                    try {
                        Group newGroup = new Group(String.valueOf(System.currentTimeMillis()), groupName, groupColor, groupDescription);
                        GroupController.addGroup(CreateGroupActivity.this, newGroup);

                        Toast.makeText(CreateGroupActivity.this, "Group created", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(CreateGroupActivity.this, "Failed to create group", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateGroupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
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