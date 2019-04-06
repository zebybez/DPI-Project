package shared.domain;

public class AttendRequest {
    private String eventId;
    private String name;
    private String email;

    public String getEventId() {
        return eventId;
    }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public AttendRequest(String eventId, String name, String email) {
        this.eventId = eventId;
        this.name = name;
        this.email = email;
    }
}
