package com.jpmorgan.manager;


import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Follows the Object pool pattern that uses a set of initialized Resources in a "pool"
 * rather that allocating and destroying on demand. <br/>
 * The pool pattern is using regular FIFO mechanism represented by a Blocked Queue.
 * A blocking queue adds support operation for wait queue to become non-empty when borrow object is summoned.
 *
 * @param <R> Resource
 * @see http://en.wikipedia.org/wiki/Object_pool_pattern
 */
public abstract class ResourcePool<R extends Resource> implements Pool<R> {

    private final static Logger LOGGER = Logger.getLogger(ResourcePool.class.getName());

    private BlockingQueue<R> pool;

    private Integer minIdle;

    /**
     * Creates the pool with a minimum number of resources residing in the pool.
     *
     * @param minIdle
     */
    public ResourcePool(Integer minIdle) {
        if (minIdle == null || minIdle == 0)
            throw new IllegalArgumentException("Initial size must be a positive integer");
        this.minIdle = minIdle;

        initialize(minIdle);
    }

    public static ResourcePool singleResourcePool() {
        return new ResourcePool<ResourceImpl>(1) {
            @Override
            public ResourceImpl createResource() {
                return new ResourceImpl(UUID.randomUUID().toString());
            }
        };
    }


    public R borrowResource() {
        R resource = null;
        try {
            resource = this.pool.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Pool Error", e);
            e.printStackTrace();
        }
        return resource;
    }

    public void returnResource(R resource) {
        if (resource == null) {
            throw new IllegalStateException("The resource has been disposed at this point we cannot added back to the pool");
        }
        try {
            this.pool.put(resource);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Pool Error", e);
            e.printStackTrace();
        }
    }

    /**
     * Create a new resource.
     *
     * @return R new resource
     */
    public abstract R createResource();

    public void shutdown() throws ResourceError {
        if (pool.isEmpty())
            return;

        Iterator<R> iterator = pool.iterator();
        while (iterator.hasNext()) {
            R resource = iterator.next();
            awaitTermination(resource);
            resource.dispose();
            iterator.remove();
        }
    }

    /**
     * Size of the resource pool.
     *
     * @return size of pool
     */
    public int size() {
        return minIdle;
    }

    private void initialize(final int minIdle) {
        this.pool = new LinkedBlockingQueue<>();
        for (int i = 0; i < minIdle; ++i)
            pool.add(createResource());
    }

    private void awaitTermination(R resource) {
        LOGGER.log(Level.INFO, "About to check if Resource is available for disposal");
        while (!resource.isAvailable()) {
            LOGGER.log(Level.INFO, "Resource is not available yet");
        }
        LOGGER.log(Level.INFO, "Resource is available for disposal");
    }
}