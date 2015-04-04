package org.bohao.core;

import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bohao on 04-04-0004.
 */
public class ImageControllerTest {
    private ImageController controller;

    @Before
    public void setUp() throws Exception {
        controller = new ImageController();
    }

    @Test
    public void testDoGet() throws Exception {
        HttpResponse response = new HttpResponse();
        HttpRequest request = new HttpRequest();

        // ‘› ±’“≤ªµΩ
        request.setContextPath("/image/404.png");
        controller.doGet(request, response);

        assert "image/jpeg".equals(response.getHeader("Content-Type"));
    }
}