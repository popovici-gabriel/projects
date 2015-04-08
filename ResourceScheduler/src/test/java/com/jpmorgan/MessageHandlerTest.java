package com.jpmorgan;

import com.jpmorgan.model.EndingMessage;
import com.jpmorgan.model.Group;
import com.jpmorgan.model.MessageImpl;
import com.jpmorgan.model.MessageSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class MessageHandlerTest {

    private static final String GROUP1 = "GROUP1";
    private static final String GROUP2 = "GROUP2";
    private static final String GROUP3 = "GROUP3";
    private static final String M_1 = "M1";
    private static final String M_2 = "M2";
    private static final String M_3 = "M3";


    @Before
    public void setup() {
        Group<String> group1 = new Group<>(GROUP1);
        Group<String> group2 = new Group<>(GROUP2);
        Group<String> group3 = new Group<>(GROUP3);

        MessageSequence<String, String> m1g1 = new MessageImpl<>(M_1, null, new Date(), group1);
        MessageSequence<String, String> m2g1 = new MessageImpl<>(M_2, null, new Date(), group1);
        MessageSequence<String, String> m3g1 = new EndingMessage<>(M_3, null, new Date(), group1);

        MessageSequence<String, String> m1g2 = new MessageImpl<>(M_1, null, new Date(), group2);
        MessageSequence<String, String> m2g2 = new EndingMessage<>(M_2, null, new Date(), group2);

        MessageSequence<String, String> m1g3 = new MessageImpl<>(M_1, null, new Date(), group3);
        MessageSequence<String, String> m2g3 = new EndingMessage<>(M_2, null, new Date(), group3);

    }

    @Test
    public void shouldCreateMessageHandler() {
        MessageHandler handler = new MessageHandler();
        Assert.assertNotNull(handler);
    }
}
