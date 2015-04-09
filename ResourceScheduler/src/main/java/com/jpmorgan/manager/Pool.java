package com.jpmorgan.manager;

/**
 * Follows the Object pool pattern that uses a set of initialized Resources in a "pool"
 * rather that allocating and destroying on demand.
 *
 * @param <R> Resource
 * @see http://en.wikipedia.org/wiki/Object_pool_pattern
 */
public interface Pool<R> {

    /**
     * Creates a new resource.
     *
     * @return R a new resource
     */
    R createResource();

    /**
     * Gets the next free Resource from the pool. If the pool is empty a new Resource will be
     * created.
     *
     * @return R resource
     */
    R borrowResource();

    /**
     * Returns the resource back to the pool.
     *
     * @param resource to be returned to the pool.
     */
    void returnResource(R resource);

    /**
     * Should dispose the pool and the resources.
     */
    void shutdown() throws ResourceError;

}
