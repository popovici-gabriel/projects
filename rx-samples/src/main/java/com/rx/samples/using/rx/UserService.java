package com.rx.samples.using.rx;


import rx.Observer;
import rx.functions.Action1;

/**
 * User interface main contract
 * - add user
 */
public interface UserService {
    /**
     * Add user
     *
     * @param username     SID
     * @param emailAddress JPMC email address
     */
    void addUser(String username, String emailAddress);

    /**
     * Subscribe using a custom Observer
     *
     * @param subscriber {@link rx.Observer} subscriber
     */
    void subsribeToUserEvents(Observer<UserEvent> subscriber);
}
