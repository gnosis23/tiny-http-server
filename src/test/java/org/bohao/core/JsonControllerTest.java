package org.bohao.core;

import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by bohao on 15/04/07.
 */
public class JsonControllerTest {
    private JsonController jsonController;

    @Before
    public void setUp() throws Exception {
        jsonController = new JsonController();
    }

    @Test
    public void testDoGet() throws Exception {
        HttpResponse response = new HttpResponse();
        HttpRequest request = new HttpRequest();

        // ÔÝÊ±ÕÒ²»µ½
        request.setContextPath("/image/404.png");
        jsonController.doGet(request, response);

        assert "application/json".equals(response.getHeader("Content-Type"));
        assert response.getBinaryData().length > 0;
    }
}