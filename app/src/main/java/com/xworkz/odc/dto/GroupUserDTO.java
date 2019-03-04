package com.xworkz.odc.dto;

import android.util.Log;

import java.io.Serializable;

public class GroupUserDTO implements Serializable {

    private int groupUserId;
    private int groupId;
    private int userId;

    public GroupUserDTO(int groupUserId, int groupId, int userId) {
        this.groupUserId = groupUserId;
        this.groupId = groupId;
        this.userId = userId;
    }

    public GroupUserDTO() {
        Log.d("Group User DTO",this.getClass().getSimpleName()+" is created......");
    }

    public int getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(int groupUserId) {
        this.groupUserId = groupUserId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String
    toString() {
        return "GroupUserDTO{" +
                "groupUserId=" + groupUserId +
                ", groupId=" + groupId +
                ", userId=" + userId +
                '}';
    }

}
