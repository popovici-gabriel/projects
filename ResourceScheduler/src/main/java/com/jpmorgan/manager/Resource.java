package com.jpmorgan.manager;


import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.endpoint.Message;

import java.util.Date;

public abstract class Resource {

    private String name;

    protected Date created;

    public Resource(String name) {
        this.name = name;
        this.created = new Date();
    }

    /**
     * Send message using Gateway
     *
     * @param message Message
     * @param gateway Gateway
     * @throws ResourceError for any problem
     */
    public abstract void sendMessage(Message message, Gateway gateway) throws ResourceError;

    /**
     * Checks to see whether or not the particular resource is available for usage.
     *
     * @return idle on/off
     */
    public abstract boolean isAvailable();

    /**
     * Checks to see whether or not the underlying resource is already created.
     *
     * @return created true/false
     */
    public abstract boolean isCreated();

    /**
     * Checks to see whether not the underlying resource has already been disposed.
     *
     * @return disposed true/false
     */
    public abstract boolean isDisposed();


    /**
     * This makes sure the resource is disposed when requested or Garbage Collected.
     *
     * @see http://en.wikipedia.org/wiki/Dispose_pattern
     */
    protected abstract void dispose() throws ResourceError;

    @Override
    public String toString() {
        return "Resource{" +
                "name='" + name + '\'' +
                ", created on =" + created +
                '}';
    }
}