package com.jpmorgan.model;

import java.util.Date;

public class EndingMessage<ID, P> extends MessageSequence<ID, P> {

    public EndingMessage(ID id, P payload, Date created, Group group) {
        super(id, payload, created, group);
        this.endOfMessage = true;
    }


    @Override
    public void completed() {
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}
