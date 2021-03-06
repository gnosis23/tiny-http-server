package org.bohao.io;

import org.bohao.entt.Cookie;
import org.bohao.proto.HttpRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpReaderTest {
    private HttpReader reader;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetRequest() throws Exception {
        InputStream stream = new ByteArrayInputStream((
                "GET / HTTP/1.1\n" +
                        "Host: localhost:22222\n" +
                        "User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                        "Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3\n" +
                        "Accept-Encoding: gzip, deflate\n" +
                        "Connection: keep-alive\n" +
                        "\n").getBytes(StandardCharsets.UTF_8));
        reader = new HttpReader(stream);
        HttpRequest request = reader.getRequest();

        assert request.getMethod().equals("GET");
        assert request.getContextPath().equals("/");
        assert request.getHeader("Host").equals("localhost:22222");
        assert request.getHeader("Connection").equals("keep-alive");
    }

    @Test
    public void testGetRequest2() throws Exception {
        InputStream stream = new ByteArrayInputStream((
                "POST /html/form2.html HTTP/1.1\n" +
                        "Host: localhost:22222\n" +
                        "User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                        "Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3\n" +
                        "Accept-Encoding: gzip, deflate\n" +
                        "Connection: keep-alive\n" +
                        "\n" +
                        "firstname=wang&lastname=nima"
        ).getBytes(StandardCharsets.UTF_8));
        reader = new HttpReader(stream);
        HttpRequest request = reader.getRequest();

        assert request.getMethod().equals("POST");
        assert request.getContextPath().equals("/html/form2.html");
        assert request.getHeader("Host").equals("localhost:22222");
        assert request.getHeader("Connection").equals("keep-alive");
    }

    @Test
    public void testPost() throws Exception {
        InputStream stream = new ByteArrayInputStream((
                "POST / HTTP/1.1\r\n" +
                        "Host: localhost:22222\r\n" +
                        "User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0\r\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
                        "Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3\r\n" +
                        "Accept-Encoding: gzip, deflate\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Content-Length: 28\r\n" +
                        "\r\n" +
                        "firstname=wang&lastname=nima"
        ).getBytes(StandardCharsets.UTF_8));
        reader = new HttpReader(stream);
        HttpRequest request = reader.getRequest();

        assert request.getMethod().equals("POST");
        assert request.getContextPath().equals("/");

        assert "firstname=wang&lastname=nima".equals(request.getContent());
    }

    @Test
    public void testCookie() throws Exception {
        InputStream stream = new ByteArrayInputStream((
                ""
        ).getBytes(StandardCharsets.UTF_8));
        reader = new HttpReader(stream);

        Cookie cookie = reader.parseCookie("Cookie: $Version=1; Customer=WILE_E_COYOTE; $Path=/acme");

        Assert.assertEquals(cookie.getVersion(), 1);
        Assert.assertEquals(cookie.getPath(), "/acme");
        Assert.assertEquals(cookie.getName(), "Customer");
        Assert.assertEquals(cookie.getValue(), "WILE_E_COYOTE");
    }

    @Test
    public void testCookie2() throws Exception {
        InputStream stream = new ByteArrayInputStream((
                "POST / HTTP/1.1\r\n" +
                        "Host: localhost:22222\r\n" +
                        "Accept-Encoding: gzip, deflate\r\n" +
                        "Cookie: $Version=1; Customer=WILE_E_COYOTE; $Path=/acme\r\n" +
                        "Cookie: $Version=1; Gta=Grand Theft Auto; $Path=/shanghai\r\n" +
                        "\r\n"
        ).getBytes(StandardCharsets.UTF_8));
        reader = new HttpReader(stream);

        HttpRequest request = reader.getRequest();
        List<Cookie> cookies = request.getCookies();

        Assert.assertTrue(cookies.size() == 2);
        Cookie c1 = cookies.get(0);
        Cookie c2 = cookies.get(1);

        Assert.assertEquals(c1.getVersion(), 1);
        Assert.assertEquals(c1.getPath(), "/acme");
        Assert.assertEquals(c1.getName(), "Customer");
        Assert.assertEquals(c1.getValue(), "WILE_E_COYOTE");

        Assert.assertEquals(c2.getVersion(), 1);
        Assert.assertEquals(c2.getPath(), "/shanghai");
        Assert.assertEquals(c2.getName(), "Gta");
        Assert.assertEquals(c2.getValue(), "Grand Theft Auto");
    }

    @Test(expected = RuntimeException.class)
    public void testCookie3() throws Exception {
        InputStream stream = new ByteArrayInputStream((
                "POST / HTTP/1.1\r\n" +
                        "Host: localhost:22222\r\n" +
                        "Accept-Encoding: gzip, deflate\r\n" +
                        "Cookie: $Sina=1; Customer=WILE_E_COYOTE; $Path=/acme\r\n" +
                        "\r\n"
        ).getBytes(StandardCharsets.UTF_8));
        reader = new HttpReader(stream);

        HttpRequest request = reader.getRequest();

        //Assert.assertNull(request.getCookies());
    }
}