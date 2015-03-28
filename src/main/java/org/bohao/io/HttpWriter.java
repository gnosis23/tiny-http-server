package org.bohao.io;

import org.bohao.proto.HttpResponse;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpWriter implements AutoCloseable {
    private PrintWriter writer;

    public HttpWriter(OutputStream outputStream) {
        writer = new PrintWriter(outputStream, true);
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }

    public void sendResponse(HttpResponse response) {
        // header
        writer.println(String.format("HTTP/1.1 %d OK", response.getStatus()));

        // attributes
        for (String name : response.getHeaderNames()) {
            writer.println(String.format("%s: %s", name, response.getHeader(name)));
        }
        writer.println();


        writer.println(response.getContent());
    }
}
