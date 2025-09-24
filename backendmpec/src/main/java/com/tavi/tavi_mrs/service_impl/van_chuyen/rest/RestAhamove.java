package com.tavi.tavi_mrs.service_impl.van_chuyen.rest;

import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.DataAhamove;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.Order;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.OrderAhamoveCreated;
import com.tavi.tavi_mrs.entities.van_chuyen.json.RestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@Slf4j
public class RestAhamove {

    @Autowired
    private RestTemplate template;

    private static final String SERVER_URL = "https://apistg.ahamove.com/v1";

    public Object callPostAhamove(RestBuilder rest, HttpServletRequest request, DataAhamove data) throws IOException {

        StringBuilder url = new StringBuilder(SERVER_URL + "/" + rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));

        System.out.println(url.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request2 = new HttpEntity(headers);
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url.toString());
            ResponseEntity<OrderAhamoveCreated> dataAhamove = template.postForEntity(builder.build().encode().toUriString(), request2, OrderAhamoveCreated.class);
            if (data == null) return null;
            System.out.println(dataAhamove);
            return dataAhamove.getBody();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }

    public Object callGetAhamove(RestBuilder rest) {
        StringBuilder url = new StringBuilder(SERVER_URL + "/" + rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        System.out.println(url.toString());
        try {
            ResponseEntity<Order> dataAhamove = template.getForEntity(url.toString(), Order.class);
            if (dataAhamove.getBody() == null) return null;
            System.out.println(dataAhamove);
            return dataAhamove.getBody();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }

    public void callDeleteAhamove(RestBuilder rest) {
        StringBuilder url = new StringBuilder(SERVER_URL + "/" + rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        System.out.println(url.toString());
        try {
            template.getForEntity(url.toString(), Order.class);
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }
}
