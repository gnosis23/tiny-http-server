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

        String str = path.substring(index + 1);
        for (String p : str.split("&")) {
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
        return params;
    }

    public static Map<String, String> parse(String path) {
        return parse(path, "UTF-8");
    }
}
