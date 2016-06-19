package com.rx.samples.using.rx;

import java.io.Serializable;
import java.util.Date;

/**
 * Main Domain Event class
 */
public class UserEvent implements Serializable {
    protected final String username;
    protected final String emailAddress;
    protected final Date eventDate;

    public UserEvent(String username, String emailAddress) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.eventDate = new Date();
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getEventDate() {
        return new Date(eventDate.getTime());
    }
}
