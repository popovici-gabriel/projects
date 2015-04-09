package com.jpmorgan.manager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class ResourcePoolTest {

    private static final int MIN_IDLE = 2;

    private ResourcePool<ResourceImpl> pool;

    @Before
    public void setup() {
        pool = new ResourcePool<ResourceImpl>(MIN_IDLE) {
            @Override
            public ResourceImpl createResource() {
                return new ResourceImpl(UUID.randomUUID().toString());
            }
        };
    }

    @After
    public void cleanup() throws ResourceError {
        pool.shutdown();
    }

    @Test
    public void shouldBeTwoResourcesInThePool() {
        Assert.assertSame(MIN_IDLE, pool.size());
    }

    @Test
    public void borrowedResourceShouldNotBeNull() {
        Assert.assertNotNull(pool.borrowResource());
    }

    @Test
    public void shouldPlaceBackResource() {
        // Arrange
        ResourceImpl resource = pool.borrowResource();
        // Act
        pool.returnResource(resource);
    }

}
