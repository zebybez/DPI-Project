package shared.domain;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public Event(String eventId, String name, Date date, double price, int maxAttendees) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.price = price;
        this.maxAttendees = maxAttendees;
        this.attendees = 0;
        this.latitude = 0;
        this.longitude = 0;
    }

    private String name;
    private long longitude;
    private long latitude;
    private Date date;
    private double price;
    private int attendees;
    private int maxAttendees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    public String info(){
        StringBuilder builder = new StringBuilder();
        builder.append("Event with:: Name: ")
                .append(name)
                .append("| Price: ")
                .append(price)
                .append("| Maximum people: ")
                .append(maxAttendees)
                .append("| Date: ")
                .append(date)
                .append("| eventID: ")
                .append(eventId);
        return builder.toString();
    }
}
