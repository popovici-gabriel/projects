package com.jpmorgan.priority;

import com.jpmorgan.endpoint.Message;
import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;

import java.util.Iterator;

public abstract class IteratorStrategy<E extends Message> implements Iterator<E> {

    protected final Iterator<E> iterator;

    public IteratorStrategy(final Iterator<E> iterator) {
        this.iterator = iterator;
    }

    protected static MessageImpl NULL_OBEJCT = new MessageImpl(null, null, null);

    /**
     * Returns the next element in the iteration.
     *
     * @param group Group message
     * @return the next element in the iteration
     * @throws java.util.NoSuchElementException if the iteration has no more elements
     */
    public abstract E next(Group group);

    public boolean isNotNullObject(E message) {
        return !message.equals(NULL_OBEJCT);
    }
}
