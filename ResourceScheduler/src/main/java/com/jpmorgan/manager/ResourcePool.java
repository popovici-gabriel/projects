package com.jpmorgan.manager;


import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Follows the Object pool pattern that uses a set of initialized Resources in a "pool"
 * rather that allocating and destroyin on demand.
 *
 * @param <R> Resource
 * @see http://en.wikipedia.org/wiki/Object_pool_pattern
 */
public abstract class ResourcePool<R extends Resource> implements Pool<R> {

    private Queue<R> pool;

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


    public R borrowResource() {
        R resource = this.pool.poll();
        if (resource == null) {
            resource = createResource();
        }
        return resource;
    }

    public void returnResource(R resource) {
        if (resource == null) {
            throw new IllegalStateException("The resource has been disposed at this point we cannot added back to the pool");
        }
        this.pool.offer(resource);
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
        this.pool = new ConcurrentLinkedQueue<R>();
        for (int i = 0; i < minIdle; ++i)
            pool.add(createResource());
    }
}
