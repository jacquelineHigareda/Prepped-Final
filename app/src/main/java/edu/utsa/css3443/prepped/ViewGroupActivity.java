package edu.utsa.css3443.prepped;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.List;

public class ViewGroupActivity extends AppCompatActivity implements GroupAdapter.GroupAdapterListener {
    private RecyclerView groupsRecyclerView;
    private GroupAdapter groupAdapter;
    private List<Group> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupsRecyclerView = findViewById(R.id.groupsRecyclerView);
        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            groups = GroupController.getAllGroups(this);
            groupAdapter = new GroupAdapter(this, groups, this);
            groupsRecyclerView.setAdapter(groupAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGroupChecked(String groupId, boolean isChecked) {
        if (isChecked) {
            try {
                GroupController.deleteGroup(this, groupId);
                groups = GroupController.getAllGroups(this);
                groupAdapter = new GroupAdapter(this, groups, this);
                groupsRecyclerView.setAdapter(groupAdapter);
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
