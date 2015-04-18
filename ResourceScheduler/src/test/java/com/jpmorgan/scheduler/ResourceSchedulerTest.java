package com.jpmorgan.scheduler;

import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.endpoint.GatewayMock;
import com.jpmorgan.manager.ResourceError;
import com.jpmorgan.manager.ResourceImpl;
import com.jpmorgan.manager.ResourceManager;
import com.jpmorgan.manager.ResourcePool;
import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Class under test is ResourceScheduler
 */
public class ResourceSchedulerTest {

    private ResourcePool<ResourceImpl> pool;

    private ResourceManager manager;

    private Gateway gateway;

    private Group<String> group1;

    private MessageImpl[] sequence;

    private static final int SIZE = 3;

    private ResourceScheduler scheduler;

    @Before
    public void setup() {
        pool = ResourcePool.singleResourcePool();
        manager = new ResourceManager(pool);
        gateway = new GatewayMock();
        group1 = new Group<>("G1");
        sequence = MessageImpl.createSequenceMessage(group1, SIZE);
        scheduler = new ResourceScheduler(manager, gateway);
    }

    @Test
    public void shouldAddSequence() throws TerminationMessageError {
        scheduler.sendSequence(sequence);
        for (int i = 0; i < sequence.length; i++) {
            Assert.assertTrue(sequence[i].isCompleted());
        }
    }

    @Test(expected = TerminationMessageError.class)
    public void shouldThrowErrorWhenMessageEnd() throws TerminationMessageError {
        sequence = Arrays.copyOf(sequence, 4);
        sequence[3] = MessageImpl.createSingleMessage("error", group1);
        scheduler.sendSequence(sequence);
    }


    @After
    public void release() throws ResourceError {
        manager.stop();
    }

}