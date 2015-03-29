package org.bohao.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bohao on 03-29-0029.
 */
public class FileToStr {
    private static final Logger logger = LoggerFactory.getLogger(FileToStr.class);
    private static final String EMPTY = "<html>404</html>";

    public static String resolve(String path) {
        logger.info("resolving {}", path);
        InputStream stream = FileToStr.class.getClassLoader().getResourceAsStream(path);
        String str;
        try {
            str = IOUtils.toString(stream);
        } catch (IOException | NullPointerException e) {
            logger.info("{} not exists", path);
            str = null;
        }

        if (str == null)
            str = EMPTY;

        return str;
    }
}
