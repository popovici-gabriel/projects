package com.rx.samples.using.rx;

import rx.Observer;
import rx.subjects.PublishSubject;

public class UserServiceImpl implements UserService {
    private final PublishSubject<UserEvent> userEventSubject;

    public UserServiceImpl() {
        /* creates a Pub/Sub subject in order to publish events*/
        this.userEventSubject = PublishSubject.create();
    }

    @Override
    public void addUser(String username, String emailAddress) {
        /* simulate processing operation */
        System.out.println(String.format("Add user %s with email address %s", username, emailAddress));
        /* create a new domain event to be published */
        UserEvent userEvent = new CreateUserEvent(username, emailAddress);
        /* publish the event to the user event subject */
        userEventSubject.onNext(userEvent);
    }

    @Override
    public void subsribeToUserEvents(Observer<UserEvent> subscriber) {
        userEventSubject.subscribe(subscriber);
    }
}
