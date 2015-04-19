package org.bohao.io;

import org.bohao.entt.Cookie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bohao on 15/04/18.
 */
public class HttpWriterTest {
    private HttpWriter writer;

    @Before
    public void setUp() throws Exception {
        writer = new HttpWriter(null);
    }

    @Test
    public void testPrint() throws Exception {
        Cookie cookie = new Cookie("sid", "4008123");
        Assert.assertTrue(writer.print(cookie).startsWith("Set-Cookie: sid=4008123"));
    }

    @Test
    public void testPrint2() throws Exception {
        Cookie cookie = new Cookie("sid", "4008123");
        cookie.setPath("/accounts");
        cookie.setDomain(".bili.tv");

        System.out.println(writer.print(cookie));
        Assert.assertTrue(writer.print(cookie).startsWith("Set-Cookie: sid=4008123"));
        Assert.assertTrue(writer.print(cookie).endsWith("Path=/accounts; Domain=.bili.tv\r\n"));
    }
}