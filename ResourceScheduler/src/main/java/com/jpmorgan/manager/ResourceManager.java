package com.jpmorgan.manager;

import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.endpoint.Message;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Resource Manager which uses the Object Pool pattern.
 *
 * @param <R>
 * @see com.jpmorgan.manager.ResourcePool
 */
public class ResourceManager<R extends Resource> {

    private static final Logger LOGGER = Logger.getLogger(ResourceImpl.class.getName());

    private final ResourcePool<R> pool;

    public ResourceManager(ResourcePool<R> pool) {
        this.pool = pool;
    }


    /**
     * Send message action using gateway.
     *
     * @param message Message
     * @param gateway Gateway
     */
    public void sendMessage(final Message message, final Gateway gateway) {
        LOGGER.log(Level.INFO, "About to borrow Resource from Pool");
        R resource = pool.borrowResource();
        try {
            LOGGER.log(Level.INFO, "About to execute process under resource");
            resource.sendMessage(message, gateway);
        } catch (ResourceError resourceError) {
            LOGGER.log(Level.SEVERE, "Resource Error", resourceError);
        } finally {
            LOGGER.log(Level.INFO, "About to return Resource back to pool");
            pool.returnResource(resource);
        }
    }

    public void stop() throws ResourceError {
        pool.shutdown();
    }
}
