package com.xworkz.odc.dto;

import android.util.Log;

import java.io.Serializable;

public class EventDTO implements Serializable {

    private int eventId;
    private String eventName;
    private String eventDesc;
    private String evenTime;
    private String eventDate;
    private String eventGroupId;

    public EventDTO(int eventId, String eventName, String eventDesc, String evenTime, String eventDate, String eventGroupId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDesc = eventDesc;
        this.evenTime = evenTime;
        this.eventDate = eventDate;
        this.eventGroupId = eventGroupId;
    }

    public EventDTO() {
        Log.d("Event DTO",this.getClass().getSimpleName()+" is created.......");
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEvenTime() {
        return evenTime;
    }

    public void setEvenTime(String evenTime) {
        this.evenTime = evenTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventGroupId() {
        return eventGroupId;
    }

    public void setEventGroupId(String eventGroupId) {
        this.eventGroupId = eventGroupId;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventDesc='" + eventDesc + '\'' +
                ", evenTime='" + evenTime + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventGroupId='" + eventGroupId + '\'' +
                '}';
    }
}
