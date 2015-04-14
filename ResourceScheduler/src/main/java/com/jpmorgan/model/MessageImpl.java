package com.jpmorgan.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageImpl extends MessageSequence<Long, String> {

    private AtomicBoolean completed;

    public MessageImpl(Long id, String payload, Group group) {
        super(id, payload, new Date(), group);
        this.completed = new AtomicBoolean(false);
    }

    public MessageImpl(Long id, String payload, Group group, boolean endOfMessage) {
        this(id, payload, group);
        this.endOfMessage = endOfMessage;
    }

    @Override
    public void completed() {
        this.completed.set(true);
    }

    @Override
    public boolean isCompleted() {
        return completed.get();
    }

    /**
     * Create Sequence message.
     *
     * @param id      sequence ID
     * @param payload message payload
     * @param group   owning group
     * @return MessageImpl
     */
    public static MessageImpl createSequenceMessage(Long id, String payload, Group group) {
        return new MessageImpl(id, payload, group);
    }

    /**
     * Create Single message. The end of message flag will be set to true.
     *
     * @param id      sequence ID
     * @param payload message payload
     * @param group   owning group
     * @return MessageImpl
     */
    public static MessageImpl createSingleMessage(Long id, String payload, Group group) {
        return new MessageImpl(id, payload, group, false);
    }

}
