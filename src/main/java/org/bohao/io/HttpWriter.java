package org.bohao.io;

import org.bohao.proto.HttpResponse;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpWriter implements AutoCloseable {
    // Ϊʲô����PrintWritwr��
    // ��Ϊhttp�ڿ��ܴ��ͼƬ�ȶ�������Ϣ
    // PrintWriter�ǻ����ַ����͵ģ��޷�ֱ�����������
    private OutputStream writer;

    public HttpWriter(OutputStream outputStream) {
        writer = new BufferedOutputStream(outputStream);
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }

    public void sendResponse(HttpResponse response) {
        // header
        String desc = "";
        switch (response.getStatus()) {
            case 200:
                desc = "OK";
                break;
            case 404:
                desc = "Not Found";
                break;
        }

        try {
            writer.write(encodingStringWithUtf8(String.format("HTTP/1.1 %d %S\n", response.getStatus(), desc)));

            // attributes
            for (String name : response.getHeaderNames()) {
                writer.write(encodingStringWithUtf8(String.format("%s: %s\n", name, response.getHeader(name))));
            }
            writer.write(encodingStringWithUtf8("\n"));

            switch (response.getHeader("Content-Type")) {
                case "text/html":
                    writer.write(encodingStringWithUtf8(response.getContent()));
                    break;
                case "image/png":
                case "image/jpeg":
                case "application/json":
                    writer.write(response.getBinaryData());
                    break;
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����Ҳһ����ַ�
     *
     * @param str str -> bytes
     * @return bytes
     */
    private byte[] encodingStringWithUtf8(String str) {
        return str.getBytes(Charset.forName("UTF-8"));
    }
}
