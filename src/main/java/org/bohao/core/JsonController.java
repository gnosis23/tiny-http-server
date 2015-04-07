package org.bohao.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bohao.annotation.Controller;
import org.bohao.annotation.RequestMapping;
import org.bohao.entt.TimeJson;
import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.bohao.utils.TimeUtils;
import org.joda.time.DateTime;

/**
 * Created by bohao on 15/04/07.
 */
@Controller(value = "/json")
public class JsonController {
    @RequestMapping(value = "/json/1")
    public void doGet(HttpRequest request, HttpResponse response)
            throws JsonProcessingException {
        response.setStatus(200);

        DateTime now = new DateTime();
        response.setAttribute("Date", TimeUtils.getServerTime());
        response.setAttribute("Content-Type", "application/json");
        response.setAttribute("Connection", "keep-alive");
        response.setAttribute("Server", "Bengine");
        response.setAttribute("Last-Modified", response.getHeader("Date"));


        TimeJson json = new TimeJson();
        json.setTime(now.toString());
        ObjectMapper mapper = new ObjectMapper();
        byte[] bytes = mapper.writeValueAsBytes(json);
        response.setBinaryData(bytes);
    }

}
