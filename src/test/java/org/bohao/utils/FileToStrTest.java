package org.bohao.utils;

import org.junit.Test;

/**
 * Created by bohao on 03-29-0029.
 */
public class FileToStrTest {

    @Test
    public void testResolve() throws Exception {
        String str = FileToStr.resolve("web-root/html/a.html");

        assert str != null;
        assert str.substring(0, 2).equals("<!");

        str = FileToStr.resolve("web-root/html/s.html");
        assert str.equals("<html>404</html>");
    }
}