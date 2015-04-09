package com.jpmorgan.manager;


import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.endpoint.Message;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceImpl extends Resource {

    private static final Logger LOGGER = Logger.getLogger(ResourceImpl.class.getName());

    private volatile boolean available;

    private volatile boolean disposed;

    public ResourceImpl(String name) {
        super(name);
        this.available = true;
        this.disposed = false;
    }

    @Override
    public void sendMessage(final Message message, final Gateway gateway) throws ResourceError {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> result = executor.submit(setupWorker(message, gateway));
        this.available = false;
        try {
            result.get();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Resource Error", e);
            throw new ResourceError("Resource Error", e);
        } catch (ExecutionException e) {
            LOGGER.log(Level.SEVERE, "Resource Error", e);
            throw new ResourceError("Resource Error", e);
        } finally {
            this.available = true;
        }
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean isCreated() {
        return this.created != null;
    }

    @Override
    protected void dispose() throws ResourceError {
        disposed = true;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    private ResourceWorker setupWorker(Message message, Gateway gateway) {
        return new ResourceWorker(gateway, message);
    }
}