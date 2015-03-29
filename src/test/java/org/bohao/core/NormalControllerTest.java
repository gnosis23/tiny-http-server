package org.bohao.core;

import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.junit.Before;
import org.junit.Test;

public class NormalControllerTest {
    private NormalController controller;

    @Before
    public void setUp() throws Exception {
        controller = new NormalController();
    }

    @Test
    public void testDoGet() throws Exception {
        HttpResponse response = new HttpResponse();
        controller.doGet(null, response);

        assert response.getStatus() == 200;
        assert response.getHeader("Date") != null;
        System.out.println(response.getHeader("Date"));
        System.out.println(response.getHeader("Expires"));
    }

    @Test
    public void testDoGet2() throws Exception {
        HttpResponse response = new HttpResponse();
        HttpRequest request = new HttpRequest();
        request.setContextPath("/html/form1.html");
        controller.doGet(request, response);

        assert "<!DOCTYPE".equals(response.getContent().substring(0, 9));
    }
}