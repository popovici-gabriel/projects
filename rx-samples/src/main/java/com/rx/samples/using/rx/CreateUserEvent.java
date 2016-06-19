package com.rx.samples.using.rx;

public class CreateUserEvent extends UserEvent {
    public CreateUserEvent(String username, String emailAddress) {
        super(username, emailAddress);
    }
}
