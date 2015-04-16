package com.jpmorgan;

import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.endpoint.Message;
import com.jpmorgan.manager.ResourceError;
import com.jpmorgan.manager.ResourceImpl;
import com.jpmorgan.manager.ResourceManager;
import com.jpmorgan.manager.ResourcePool;
import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;
import com.jpmorgan.scheduler.ResourceScheduler;
import com.jpmorgan.scheduler.TerminationMessageError;

import java.util.ArrayList;
import java.util.List;

/**
 * Launch the app.
 */
public class App {

    private static final int SIZE = 4;


    private static MessageImpl[] createTestCaseSequence() {
        List<MessageImpl> sequence = new ArrayList<>(4);
        Group group1 = new Group<>("G1");
        Group group2 = new Group<>("G2");
        Group group3 = new Group<>("G3");

        MessageImpl m1g2 = MessageImpl.createBlockMessage("message1", group2);
        MessageImpl m2g1 = MessageImpl.createSingleMessage("message2", group1);
        MessageImpl m3g2 = MessageImpl.createSingleMessage("message3", group2);
        MessageImpl m4g3 = MessageImpl.createSingleMessage("message4", group3);
        sequence.add(m1g2);
        sequence.add(m2g1);
        sequence.add(m3g2);
        sequence.add(m4g3);

        return sequence.toArray(new MessageImpl[4]);
    }

    public static void main(String[] args) throws TerminationMessageError, ResourceError {
        System.out.println("########## BEGIN Resource Scheduler #############");

        run();

        System.out.println("########## END Resource Scheduler #############");
    }

    private static void run() throws TerminationMessageError, ResourceError {
        ResourcePool<ResourceImpl> pool = ResourcePool.singleResourcePool();
        ResourceManager manager = new ResourceManager(pool);
        Gateway gateway = new Gateway() {
            @Override
            public void send(Message message) {
                if (message == null)
                    throw new IllegalArgumentException("Message should not be null");
                message.completed();
            }
        };
        ResourceScheduler scheduler = new ResourceScheduler(manager, gateway);
        MessageImpl[] sequence = createTestCaseSequence();
        scheduler.sendSequence(sequence);

        for (int i = 0; i < sequence.length; i++) {
            System.out.println("-> Message: " + sequence[i].getId() + " processed: " + sequence[i].isCompleted());
            assert sequence[i].isCompleted() : "Error: not all messages have been processed";
        }
        manager.stop();
    }
}