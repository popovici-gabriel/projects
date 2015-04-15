package com.jpmorgan.priority;

import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;

import java.util.Iterator;

public class GroupIteratorStrategy extends IteratorStrategy<MessageImpl> {

    public GroupIteratorStrategy(Iterator<MessageImpl> iterator) {
        super(iterator);
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public MessageImpl next(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Group must not be empty");
        }

        while (hasNext()) {
            MessageImpl message = next();
            if (message.getGroup().equals(group)) {
                return message;
            }
        }
        return NULL_OBEJCT;
    }


    @Override
    public MessageImpl next() {
        return iterator.next();
    }

    public void remove() {
        iterator.remove();
    }
}
