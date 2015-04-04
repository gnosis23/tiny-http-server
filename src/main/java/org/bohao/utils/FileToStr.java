package org.bohao.utils;

import org.apache.commons.io.IOUtils;
import org.bohao.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bohao on 03-29-0029.
 */
public class FileToStr {
    private static final Logger logger = LoggerFactory.getLogger(FileToStr.class);

    public static String resolve(String path) {
        logger.info("resolving {}", path);
        InputStream stream = FileToStr.class.getClassLoader().getResourceAsStream(path);
        String str;
        try {
            str = IOUtils.toString(stream);
        } catch (IOException | NullPointerException e) {
            logger.info("{} not exists", path);
            throw new ResourceNotFoundException(path + " not found");
        }


        return str;
    }

    /**
     * ��ȡͼƬ��������Ϣ
     *
     * @param path ͼƬ·��
     * @return ����ͼƬ�ֽڻ��׳�IOException
     */
    public static byte[] image(String path) {
        InputStream stream = FileToStr.class.getClassLoader().getResourceAsStream(path);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        int len = 1024 * 1024; // 1 MB
        byte[] data = new byte[len];
        try {
            // InputStream -> Byte[]
            // code from: http://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
            //
            while ((nRead = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            logger.info("{} not exists", path);
            throw new ResourceNotFoundException(path + "not found");
        }

        return buffer.toByteArray();
    }
}
