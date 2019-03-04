package com.xworkz.odc.dto;

import android.util.Log;

import java.io.Serializable;

public class GroupDTO implements Serializable {
    private int groupId;
    private String groupName;
    private String groupDesc;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    @Override
    public String toString() {
        return "GroupDTO{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", groupDesc='" + groupDesc + '\'' +
                '}';
    }

    public GroupDTO(int groupId, String groupName, String groupDesc) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupDesc = groupDesc;
    }

    public GroupDTO() {
        Log.i("DTO",this.getClass().getCanonicalName()+" is created.........");
    }
}
