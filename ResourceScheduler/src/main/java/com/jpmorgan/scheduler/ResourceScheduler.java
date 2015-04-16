package com.jpmorgan.scheduler;

import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.manager.ResourceManager;
import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class ResourceScheduler {

    private static final Logger LOGGER = Logger.getLogger(ResourceScheduler.class.getName());

    private static final int EMPTY = 0;

    private final Gateway gateway;

    private final ResourceManager resourceManager;

    private final List<Group<Long>> cancelledGroup;

    private final List<Group<String>> terminatedGroup;

    /**
     * Messages are placed in Output queue for processing
     */
    private final BlockingQueue<MessageImpl> output;

    /**
     * Messages are placed in Input queue for filtering/ordering
     */
    private BlockingQueue<MessageImpl> input;

    private volatile int sequenceSize;

    private CountDownLatch latch;

    public ResourceScheduler(ResourceManager resourceManager, Gateway gateway) {
        this.resourceManager = resourceManager;
        this.gateway = gateway;
        this.output = new ArrayBlockingQueue<>(10);
        this.cancelledGroup = new CopyOnWriteArrayList<>();
        this.terminatedGroup = new CopyOnWriteArrayList<>();
    }

    public void sendSequence(MessageImpl... messages) throws TerminationMessageError {
        latch = new CountDownLatch(2);
        this.input = new ArrayBlockingQueue<>(messages.length);
        this.sequenceSize = messages.length;

        for (int i = 0; i < messages.length; ++i) {
            addMessage(messages[i], i);
        }

        startProducer();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancelGroup(Group group) {
        this.cancelledGroup.add(group);
    }

    private void addMessage(MessageImpl message, int index) throws TerminationMessageError {
        if (isFirstMessage(index)) {
            startConsumer();
        }

        if (isTerminatedGroup(message)) {
            throw new TerminationMessageError("Message has already been processed. No further messages allowed.");
        }

        if (message.isEndOfMessage()) {
            terminatedGroup.add(message.getGroup());
        }

        input.add(message);
    }

    private boolean isFirstMessage(int index) {
        return index == EMPTY;
    }

    private void startConsumer() {
        Thread thread = new Thread(new QueueConsumer(output, resourceManager, gateway, sequenceSize, latch));
        thread.start();
    }

    private void startProducer() {
        Thread thread = new Thread(new QueueProducer(input, output, cancelledGroup, sequenceSize, latch));
        thread.start();
    }

    private boolean isTerminatedGroup(MessageImpl message) {
        return terminatedGroup.contains(message.getGroup());
    }
}