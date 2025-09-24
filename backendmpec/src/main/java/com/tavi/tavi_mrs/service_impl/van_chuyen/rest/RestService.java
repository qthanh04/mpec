package com.tavi.tavi_mrs.service_impl.van_chuyen.rest;

import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonResult;
import com.tavi.tavi_mrs.entities.van_chuyen.json.RestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class RestService {

    @Autowired
    private RestTemplate template;

    private static final String SERVER_URL = "https://tomcat.bksoftwarevn.com/";

    public Object callGet(RestBuilder rest) {
        StringBuilder url = new StringBuilder(SERVER_URL + rest.getService() + "/" + rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        System.out.println(url.toString());
        try {
            JsonResult data = template.getForObject(url.toString(), JsonResult.class);
            if (data.getData() == null) return null;
            return data.getData();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }

    public List<Object> callGetList(RestBuilder rest) {
        StringBuilder url = new StringBuilder(SERVER_URL + rest.getService() + "/" + rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        System.out.println(url.toString());
        try {
            JsonResult data = template.getForObject(url.toString(), JsonResult.class);
            if (data.getData() == null) return null;
            return (List<Object>) data.getData();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }
}
