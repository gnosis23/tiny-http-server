package org.bohao.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by bohao on 15/04/08.
 */
public class UrlToolsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testParse() throws Exception {
        Map<String, String> maps = UrlTools.parse("/html/get?firstname=wang&lastname=nima");

        assert "wang".equals(maps.get("firstname"));
        assert "nima".equals(maps.get("lastname"));
    }

    @Test
    public void testParse2() throws Exception {
        Map<String, String> maps = UrlTools.parse("/html/get?firstname=&lastname=");

        assert "".equals(maps.get("firstname"));
        assert "".equals(maps.get("lastname"));
        assert maps.get("shit") == null;
    }

    @Test
    public void testParse3() throws Exception {
        // 使用UTF-8编码值，左边的值用英语
        Map<String, String> maps = UrlTools.parse("/html/get?firstname=%E5%92%8C%E5%B0%9A&lastname=%E8%80%81%E5%A4%B4");

        assert "和尚".equals(maps.get("firstname"));
        assert "老头".equals(maps.get("lastname"));
    }


}