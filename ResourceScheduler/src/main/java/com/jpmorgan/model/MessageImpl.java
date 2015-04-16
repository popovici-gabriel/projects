package com.jpmorgan.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageImpl extends MessageSequence<String, String> {

    private AtomicBoolean completed;

    public MessageImpl(String id, String payload, Group group) {
        super(id, payload, new Date(), group);
        this.completed = new AtomicBoolean(false);
    }

    public MessageImpl(String id, String payload, Group group, boolean endOfMessage) {
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
     * @param group owning group
     * @return MessageImpl
     */
    public static MessageImpl[] createSequenceMessage(Group group, int size) {
        List<MessageImpl> sequence = new ArrayList<>(size);
        for (int i = 1; i <= size; ++i) {
            String payload;
            if (i == 1)
                payload = MessageType.START_SEQ.toString();
            else if (i == size)
                payload = MessageType.STOP_SEQ.toString();
            else
                payload = MessageType.BLOCK_SEQ.toString();

            sequence.add(new MessageImpl(String.valueOf(i), payload, group, i == size ? true : false));
        }
        return sequence.toArray(new MessageImpl[size]);
    }

    /**
     * Create Single message. The end of message flag will be set to true.
     *
     * @param id      sequence ID
     * @param payload message payload
     * @param group   owning group
     * @return MessageImpl
     */
    public static MessageImpl createSingleMessage(String id, Group group) {
        return new MessageImpl(id, MessageType.STOP_SEQ.toString(), group, true);
    }

    /**
     * Create Single message. The end of message flag will be set to true.
     *
     * @param id    sequence ID
     * @param group owning group
     * @return MessageImpl
     */
    public static MessageImpl createBlockMessage(String id, Group group) {
        return new MessageImpl(id, MessageType.BLOCK_SEQ.toString(), group, false);
    }
}