package com.jpmorgan.manager;


import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.endpoint.Message;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceImpl extends Resource {

    private static final Logger LOGGER = Logger.getLogger(ResourceImpl.class.getName());

    private AtomicBoolean available;

    private AtomicBoolean disposed;

    public ResourceImpl(String name) {
        super(name);
        this.available = new AtomicBoolean(true);
        this.disposed = new AtomicBoolean(false);
    }

    @Override
    public void sendMessage(final Message message, final Gateway gateway) throws ResourceError {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> result = executor.submit(setupWorker(message, gateway));
        this.available.set(false);
        try {
            result.get();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Resource Error", e);
            throw new ResourceError("Resource Error", e);
        } catch (ExecutionException e) {
            LOGGER.log(Level.SEVERE, "Resource Error", e);
            throw new ResourceError("Resource Error", e);
        } finally {
            this.available.set(true);
        }
    }

    @Override
    public boolean isAvailable() {
        return available.get();
    }

    @Override
    public boolean isCreated() {
        return this.created != null;
    }

    @Override
    protected void dispose() throws ResourceError {
        disposed.set(true);
    }

    @Override
    public boolean isDisposed() {
        return disposed.get();
    }

    private ResourceWorker setupWorker(Message message, Gateway gateway) {
        return new ResourceWorker(gateway, message);
    }
}