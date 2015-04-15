package com.jpmorgan.scheduler;

import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.manager.ResourceManager;
import com.jpmorgan.model.MessageImpl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueueConsumer implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(QueueConsumer.class.getName());

    private static final int EMPTY = 0;

    private final BlockingQueue<MessageImpl> queue;

    private final ResourceManager resourceManager;

    private final Gateway gateway;

    private final AtomicInteger size;

    private final CountDownLatch latch;

    /**
     * Consumer c-tor.
     *
     * @param queue           BlockingQueue
     * @param resourceManager ResourceManager
     * @param gateway         Gateway
     * @param latch
     */
    public QueueConsumer(BlockingQueue<MessageImpl> queue, ResourceManager resourceManager, Gateway gateway, int size, CountDownLatch latch) {
        this.queue = queue;
        this.resourceManager = resourceManager;
        this.gateway = gateway;
        this.latch = latch;
        this.size = new AtomicInteger(size);
    }

    @Override
    public void run() {
        try {
            LOGGER.log(Level.INFO, "QueueConsumer: Begin Job.");
            LOGGER.log(Level.INFO, "QueueConsumer: entering sleep");
            Thread.sleep(1000);
            LOGGER.log(Level.INFO, "QueueConsumer: Wake up");
            LOGGER.log(Level.INFO, "QueueConsumer: Waiting for messages ... ");
            while (hasElements()) {
                MessageImpl message = queue.take();
                LOGGER.log(Level.INFO, "QueueConsumer: << About to send Message sequence: " + message.getId() + " under Group: " + message.getGroupId());
                resourceManager.sendMessage(message, gateway);
                LOGGER.log(Level.INFO, "QueueConsumer: << Message Sent sequence: " + message.getId() + " under Group: " + message.getGroupId());
                size.decrementAndGet();
            }
            LOGGER.log(Level.INFO, "QueueConsumer: End Job.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            latch.countDown();
        }
    }

    private boolean hasElements() {
        return size.get() != EMPTY;
    }
}
