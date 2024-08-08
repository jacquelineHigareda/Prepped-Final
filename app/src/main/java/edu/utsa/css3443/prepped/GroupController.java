package edu.utsa.css3443.prepped;

import android.content.Context;

import java.io.IOException;
import java.util.List;

public class GroupController {
    private static final String GROUPS_FILE_PATH = "groups.csv";

    public static void addGroup(Context context, Group group) throws IOException {
        Group.writeGroupToCSV(context, group, GROUPS_FILE_PATH);
    }

    public static List<Group> getAllGroups(Context context) throws IOException {
        return Group.readGroupsFromCSV(context, GROUPS_FILE_PATH);
    }

    public static void deleteGroup(Context context, String groupId) throws IOException {
        Group.deleteGroupFromCSV(context, groupId, GROUPS_FILE_PATH);
    }

    public static void updateGroup(Context context, Group updatedGroup) throws IOException {
        List<Group> groups = getAllGroups(context);

        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            if (group.getGroupId().equals(updatedGroup.getGroupId())) {
                groups.set(i, updatedGroup);
                break;
            }
        }

        Group.writeAllGroupsToCSV(context, groups, GROUPS_FILE_PATH);
    }
}
