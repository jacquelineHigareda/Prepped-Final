package edu.utsa.css3443.prepped;

import android.os.Bundle;
import android.util.Log;
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
                createGroup();
            }
        });
    }

    private void createGroup() {
        String groupName = groupNameEditText.getText().toString().trim();
        String groupColor = groupColorEditText.getText().toString().trim();
        String groupDescription = groupDescriptionEditText.getText().toString().trim();
        String groupId = groupName;

        if (!groupName.isEmpty() && !groupColor.isEmpty() && !groupDescription.isEmpty()) {
            Group newGroup = new Group(groupId, groupName, groupColor, groupDescription);
            try {
                GroupController.addGroup(this, newGroup);
                finish();
            } catch (IOException e) {
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
