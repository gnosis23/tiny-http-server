package org.bohao.core;

import org.junit.Before;
import org.junit.Test;

public class HttpServerTest {


    private HttpServer server;

    @Before
    public void setUp() throws Exception {
        server = new HttpServer(22223);
    }

    @Test
    public void testStart() throws Exception {
        //server.start();
    }
}