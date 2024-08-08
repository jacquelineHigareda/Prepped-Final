package edu.utsa.css3443.prepped;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button viewGroupButton;
    private Button createGroupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewGroupButton = findViewById(R.id.viewGroupButton);
        createGroupButton = findViewById(R.id.createGroupButton);

        viewGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewGroupActivity.class);
                startActivity(intent);
            }
        });

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateGroupActivity.class);
                startActivity(intent);
            }
        });

        try {
            List<Group> groups = GroupController.getAllGroups(this);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading groups", Toast.LENGTH_SHORT).show();
        }
    }
}
