package com.develop.instagramtest.Model;

public class Event {
    private String eventId;
    private String eventImage;
    private String eventDescription;
    private String eventPublisher;

    public Event(String eventId, String eventImage, String eventDescription, String eventPublisher) {
        this.eventId = eventId;
        this.eventImage = eventImage;
        this.eventDescription = eventDescription;
        this.eventPublisher = eventPublisher;
    }

    public Event() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventPublisher() {
        return eventPublisher;
    }

    public void setEventPublisher(String eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
