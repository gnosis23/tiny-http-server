package org.bohao.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bohao on 15/04/08.
 */
public class UrlTools {
    /**
     * @param path 完整的带问号的url，比如 /html/get?a=b&c=d
     * @return 一定会返回Map对象
     */
    public static Map<String, String> parse(String path, String encode) {
        Map<String, String> params = new HashMap<>();
        int index = path.indexOf("?");
        if (index == -1) {
            return params;
        }

        getParams(path.substring(index + 1), encode, params);
        return params;
    }

    /**
     * @param sequences eg: a=b&c=xxx
     * @param encode    eg: UTF-8
     * @param params    map
     */
    private static void getParams(String sequences, String encode, Map<String, String> params) {
        for (String p : sequences.split("&")) {
            String[] temps = p.split("=");
            if (temps.length == 1) {
                params.put(temps[0], "");
            } else {
                try {
                    params.put(temps[0], URLDecoder.decode(temps[1], encode));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("URL Decoder 编码输错了");
                }
            }
        }
    }

    public static Map<String, String> parse(String path) {
        return parse(path, "UTF-8");
    }

    public static Map<String, String> parsePost(String path) {
        return parsePost(path, "UTF-8");
    }

    private static Map<String, String> parsePost(String path, String encode) {
        Map<String, String> params = new HashMap<>();
        getParams(path, encode, params);
        return params;
    }
}
