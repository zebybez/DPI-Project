package shared.domain;

import java.io.Serializable;

public class AttendRequest implements Serializable {
    private String eventId;
    private String email;

    public String getEventId() {
        return eventId;
    }

    public String getEmail() {
        return email;
    }


    public AttendRequest(String eventId, String email) {
        this.eventId = eventId;
        this.email = email;
    }
}
