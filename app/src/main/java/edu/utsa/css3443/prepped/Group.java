package edu.utsa.css3443.prepped;

import android.content.Context;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Group {
    private String groupId;
    private String groupName;
    private String groupColor;
    private String groupDescription;
    private List<Event> events;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupColor() {
        return groupColor;
    }

    public void setGroupColor(String groupColor) {
        this.groupColor = groupColor;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public static void writeGroupToCSV(Context context, Group group, String filePath) throws IOException {
        File file = new File(context.getFilesDir(), filePath);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(group.toCSVString() + "\n");
        }
    }

    public static List<Group> readGroupsFromCSV(Context context, String filePath) throws IOException {
        File file = new File(context.getFilesDir(), filePath);
        List<Group> groups = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 4) {
                    Group group = new Group(data[0], data[1], data[2], data[3]);
                    groups.add(group);
                }
            }
        }
        return groups;
    }

    public static void deleteGroupFromCSV(Context context, String groupId, String filePath) throws IOException {
        File file = new File(context.getFilesDir(), filePath);
        List<Group> groups = readGroupsFromCSV(context, filePath);
        groups.removeIf(group -> group.getGroupId().equals(groupId));
        writeAllGroupsToCSV(context, groups, filePath);
    }

    public static void writeAllGroupsToCSV(Context context, List<Group> groups, String filePath) throws IOException {
        File file = new File(context.getFilesDir(), filePath);
        try (FileWriter writer = new FileWriter(file)) {
            for (Group group : groups) {
                writer.write(group.toCSVString() + "\n");
            }
        }
    }

    public String toCSVString() {
        return groupId + "," + groupName + "," + groupColor + "," + groupDescription;
    }

    public Group(String groupId, String groupName, String groupColor, String groupDescription) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupColor = groupColor;
        this.groupDescription = groupDescription;
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return groupName;
    }

}
