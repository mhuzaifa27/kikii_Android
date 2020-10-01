
package com.example.kikkiapp.Callbacks;

import java.util.List;

import com.example.kikkiapp.Model.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallbackGetEvents {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("next_offset")
    @Expose
    private Integer nextOffset;
    @SerializedName("events")
    @Expose
    private List<Event> events = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
