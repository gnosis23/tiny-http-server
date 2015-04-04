package org.bohao.utils;

import org.bohao.exception.ResourceNotFoundException;
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

    }


    @Test(expected = ResourceNotFoundException.class)
    public void testException() throws Exception {
        FileToStr.resolve("web-root/html/s.html");
    }
}