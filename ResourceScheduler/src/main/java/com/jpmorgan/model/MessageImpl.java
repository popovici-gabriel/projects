package com.jpmorgan.model;

import java.util.Date;

public class MessageImpl<ID, P> extends MessageSequence<ID, P> {

    private volatile boolean completed = false;

    public MessageImpl(ID id, P payload, Date created, Group group) {
        super(id, payload, created, group);
    }


    @Override
    public void completed() {
        this.completed = true;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }
}
