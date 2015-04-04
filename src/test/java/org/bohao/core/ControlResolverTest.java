package org.bohao.core;

import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bohao on 03-29-0029.
 */
public class ControlResolverTest {
    private ControlResolver resolver;

    @Before
    public void setUp() throws Exception {
        resolver = new ControlResolver();
    }

    @Test
    public void testProcess() throws Exception {
        HttpRequest request = new HttpRequest();
        HttpResponse response = new HttpResponse();
        resolver.process(request, response);
    }

    @Test
    public void testProcess2() throws Exception {
        HttpResponse response = new HttpResponse();
        HttpRequest request = new HttpRequest();
        request.setContextPath("/html/form1.html");
        resolver.process(request, response);

        assert "<!DOCTYPE".equals(response.getContent().substring(0, 9));
        // path prefix test
        assert response.getContent().contains("form");

        response = new HttpResponse();
        request.setContextPath("/html/nihao.html");
        resolver.process(request, response);
        assert response.getStatus() == 404;
    }

    @Test
    public void testProcess3() throws Exception {
        HttpResponse response = new HttpResponse();
        HttpRequest request = new HttpRequest();
        request.setContextPath("/hehe/");
        resolver.process(request, response);

        assert response.getStatus() == 404;
    }

    @Test
    public void testProcess4() throws Exception {
        HttpResponse response = new HttpResponse();
        HttpRequest request = new HttpRequest();

        // ÔÝÊ±ÕÒ²»µ½
        request.setContextPath("/image/123.jpg");
        resolver.process(request, response);

        assert response.getStatus() == 404;
    }
}