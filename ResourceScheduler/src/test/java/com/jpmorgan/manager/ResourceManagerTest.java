package com.jpmorgan.manager;

import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.endpoint.GatewayMock;
import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class ResourceManagerTest {

    private static final int MIN_IDLE = 2;

    private ResourcePool<ResourceImpl> pool;

    private ResourceManager manager;

    private Gateway gateway;

    private MessageImpl message;

    private Group<String> group;

    private static final String GROUP1 = "GROUP1";

    private static final String M_1 = "M1";


    @Before
    public void setup() {
        pool = new ResourcePool<ResourceImpl>(MIN_IDLE) {
            @Override
            public ResourceImpl createResource() {
                return new ResourceImpl(UUID.randomUUID().toString());
            }
        };
        manager = new ResourceManager(pool);
        gateway = new GatewayMock();
        group = new Group<>(GROUP1);
        message = new MessageImpl(1l, null, group);
    }

    @After
    public void cleanup() throws ResourceError {
        manager.stop();
    }


    @Test
    public void shouldSendMessage() {
        //Arrange & Act
        manager.sendMessage(message, gateway);
        //Assert
        Assert.assertTrue(message.isCompleted());
    }


}
