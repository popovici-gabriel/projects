package com.jpmorgan.model;

import com.jpmorgan.endpoint.Message;

import java.io.Serializable;
import java.util.Date;

/**
 * Message sequence model which will be sent to the Gateway.
 *
 * @param <ID> ID of the Message
 * @param <P>  Payload of the Message.
 */
public abstract class MessageSequence<ID, P> implements Message, Serializable {

    private final ID id;

    private final Date created;

    private final Group<ID> group;

    private final P payload;

    protected boolean endOfMessage;

    protected MessageSequence(ID id, P payload, Date created, Group group) {
        this.id = id;
        this.payload = payload;
        this.created = created;
        this.group = group;
        this.endOfMessage = false;
    }


    public ID getGroupId() {
        return group.getId();
    }


    public P getPayload() {
        return payload;
    }

    /**
     * If message is End Of Message this function should return false, true otherwise.
     *
     * @return tests whether next occurring message sequence is available
     */
    public boolean hasNext() {
        return !this.endOfMessage;
    }

    /**
     * Test whether or not the message has been already processed.
     *
     * @return processed status
     */
    public abstract boolean isCompleted();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageSequence that = (MessageSequence) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
