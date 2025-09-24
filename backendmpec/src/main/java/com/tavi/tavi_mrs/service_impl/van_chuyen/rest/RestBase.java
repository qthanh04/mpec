package com.tavi.tavi_mrs.service_impl.van_chuyen.rest;

import com.tavi.tavi_mrs.entities.van_chuyen.json.RestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class RestBase<T> {

    @Autowired
    private RestTemplate template;

    private Class clazz;


    public List<Object> callGet(RestBuilder rest, HttpServletRequest request, String SERVER_URL) {

        StringBuilder url = new StringBuilder(SERVER_URL + rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        System.out.println(url.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", request.getHeader("token"));
        HttpEntity entity = new HttpEntity(headers);
        ;
        try {
            ResponseEntity<T> data = template.exchange(url.toString(), HttpMethod.GET, entity, clazz);
            if (data.getBody().getClass() == null) return null;
            return (List<Object>) data.getBody();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }
}
