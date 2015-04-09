package com.jpmorgan.manager;


import com.jpmorgan.endpoint.Gateway;
import com.jpmorgan.endpoint.Message;

public final class ResourceWorker implements Runnable {
    private final Gateway gateway;
    private final Message message;

    public ResourceWorker(Gateway gateway, Message message) {
        this.gateway = gateway;
        this.message = message;
    }

    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error simulating a long process", e);
        }
        //TODO add if (!msg.isCompleted())
        gateway.send(message);
    }
}
