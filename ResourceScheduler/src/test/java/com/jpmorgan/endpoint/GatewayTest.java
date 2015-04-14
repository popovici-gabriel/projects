package com.jpmorgan.endpoint;

import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;
import com.jpmorgan.model.MessageSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GatewayTest {

    private Gateway gateway;
    private Group<String> G1;

    @Before
    public void setup() {
        this.gateway = new GatewayMock();
        this.G1 = new Group<>("G1");
    }

    @Test
    public void shouldSendMessage() {
        //arrange
        MessageSequence<Long, String> message = new MessageImpl(1l, "Hello World !", G1);
        //act
        this.gateway.send(message);
        //assert
        Assert.assertTrue(message.isCompleted());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldSendError() {
        this.gateway.send(null);
    }
}
