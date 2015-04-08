package com.jpmorgan.endpoint;

import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class MessageTest {
    private Message message;
    private Group<String> G1;

    @Before
    public void setup() {
        G1 = new Group<>("G1");
        message = new MessageImpl<>("M1", "DATA", new Date(), G1);
    }

    @Test
    public void shouldReturnSameGroupID() {
        Assert.assertSame(G1.getId(), ((MessageImpl) message).getGroupId());
    }

    @Test
    public void notEndOfMessage() {
        Assert.assertTrue(((MessageImpl) message).hasNext());
    }

    @Test
    public void shouldTestCompleted() {
        //Act
        message.completed();
        //Assert
        Assert.assertTrue(((MessageImpl) message).isCompleted());
    }

}
