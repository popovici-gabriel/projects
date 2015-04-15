package com.jpmorgan.scheduler;

import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;
import com.jpmorgan.priority.GroupIteratorStrategy;
import com.jpmorgan.priority.IteratorStrategy;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueueProducer implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(QueueProducer.class.getName());

    private static final int EMPTY = 0;

    private final BlockingQueue<MessageImpl> input;

    private final BlockingQueue<MessageImpl> output;

    private final List<Group<Long>> cancelledGroup;

    private final AtomicInteger size;

    private final CountDownLatch latch;

    public QueueProducer(BlockingQueue<MessageImpl> input, BlockingQueue<MessageImpl> output, List<Group<Long>> cancelledGroup, int size, CountDownLatch latch) {
        this.input = input;
        this.output = output;
        this.cancelledGroup = cancelledGroup;
        this.size = new AtomicInteger(size);
        this.latch = latch;
    }


    @Override
    public void run() {
        try {
            LOGGER.log(Level.INFO, "QueueProducer: Begin Job.");
            LOGGER.log(Level.INFO, "QueueProducer: entering sleep");
            Thread.sleep(1000);
            LOGGER.log(Level.INFO, "QueueProducer: Wake up");
            LOGGER.log(Level.INFO, "QueueProducer: Waiting for messages ... ");
            while (hasElements()) {

                MessageImpl head = input.take();
                size.decrementAndGet();

                if (notCancelledGroup(head)) {
                    LOGGER.log(Level.INFO, "QueueProducer: >> Message sequence: " + head.getId() + " under Group: " + head.getGroupId());
                    output.put(head);
                    Group<String> group = head.getGroup();

                    IteratorStrategy<MessageImpl> strategy = new GroupIteratorStrategy(input.iterator());
                    while (strategy.hasNext()) {
                        MessageImpl message = strategy.next(group);
                        if (strategy.isNotNullObject(message)) {
                            LOGGER.log(Level.INFO, "QueueProducer: >> Message sequence: " + message.getId() + " under Group: " + message.getGroupId());
                            output.put(message);
                            strategy.remove();
                            size.decrementAndGet();
                        }
                    }
                }
            }
            LOGGER.log(Level.INFO, "QueueProducer: End Job.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }

    private boolean hasElements() {
        return size.get() != EMPTY;
    }

    private boolean notCancelledGroup(MessageImpl message) {
        return !cancelledGroup.contains(message.getGroup());
    }
}
