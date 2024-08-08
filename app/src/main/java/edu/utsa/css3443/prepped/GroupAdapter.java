package edu.utsa.css3443.prepped;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private Context context;
    private List<Group> groups;
    private GroupAdapterListener listener;

    public interface GroupAdapterListener {
        void onGroupChecked(String groupId, boolean isChecked);
    }

    public GroupAdapter(Context context, List<Group> groups, GroupAdapterListener listener) {
        this.context = context;
        this.groups = groups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groups.get(position);

        holder.groupNameTextView.setText(group.getGroupName());
        holder.groupDescriptionTextView.setText(group.getGroupDescription());
        holder.groupEventCountTextView.setText("Events: " + group.getEvents().size());

        try {
            holder.itemView.setBackgroundColor(Color.parseColor(group.getGroupColor()));
        } catch (IllegalArgumentException e) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        StringBuilder eventsStringBuilder = new StringBuilder();
        for (Event event : group.getEvents()) {
            eventsStringBuilder.append("- ")
                    .append(event.getEventName())
                    .append(" (")
                    .append(event.getEventDate())
                    .append(")\n");
        }
        holder.groupEventsTextView.setText(eventsStringBuilder.toString().trim());

        holder.groupCheckBox.setOnCheckedChangeListener(null);
        holder.groupCheckBox.setChecked(false);

        holder.groupCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onGroupChecked(group.getGroupId(), isChecked);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView groupNameTextView;
        TextView groupDescriptionTextView;
        TextView groupEventCountTextView;
        TextView groupEventsTextView;
        CheckBox groupCheckBox;

        GroupViewHolder(View itemView) {
            super(itemView);
            groupNameTextView = itemView.findViewById(R.id.groupNameTextView);
            groupDescriptionTextView = itemView.findViewById(R.id.groupDescriptionTextView);
            groupEventCountTextView = itemView.findViewById(R.id.groupEventCountTextView);
            groupEventsTextView = itemView.findViewById(R.id.groupEventsTextView);
            groupCheckBox = itemView.findViewById(R.id.groupCheckBox);
        }
    }
}