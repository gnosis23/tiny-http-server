package org.bohao.core;

import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bohao on 15/04/19.
 */
public class TaskTest {
    private Task task;

    @Before
    public void setUp() throws Exception {
        task = new Task(null, new ConcurrentHashMap<>());
    }

    @Test
    public void testManageSession() throws Exception {
        HttpRequest request1 = new HttpRequest();
        HttpRequest request2 = new HttpRequest();
        HttpResponse response = new HttpResponse();

        task.manageSession(request1, response);
        task.manageSession(request2, response);

        Assert.assertNotNull(request1.getSession().getId());
        Assert.assertEquals(request1.getSession().getAttribute("cnt"), 0);
        Assert.assertEquals(request2.getSession().getAttribute("cnt"), 0);

        Assert.assertNotEquals(request1.getSession(), request2.getSession());

    }
}