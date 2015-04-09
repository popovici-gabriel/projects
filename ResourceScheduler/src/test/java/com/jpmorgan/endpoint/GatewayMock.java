package com.jpmorgan.endpoint;

public class GatewayMock implements Gateway {

    @Override
    public void send(Message message) {
        if (message == null)
            throw new IllegalArgumentException("Message should not be null");
        message.completed();
    }
}