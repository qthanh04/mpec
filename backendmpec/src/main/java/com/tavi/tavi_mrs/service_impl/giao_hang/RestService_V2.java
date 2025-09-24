package com.tavi.tavi_mrs.service_impl.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Partner_V2;
import com.tavi.tavi_mrs.entities.giao_hang.json.JsonGHN;
import com.tavi.tavi_mrs.entities.giao_hang.json.RestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class RestService_V2 {

    @Autowired
    private RestTemplate template;

    public String callPost(RestBuilder rest, HttpServletRequest request , String jsonObject, Partner_V2 partner) {
        StringBuilder url = new StringBuilder(partner.getUrl() +  rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        System.out.println(url.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String data;
        try {
            switch (partner.getType()){
                case 1:
                    headers.add("token",request.getHeader(partner.getTokenName()));
                    headers.add("ShopId",request.getHeader("ShopId"));
                    HttpEntity<String> requestGHN =
                            new HttpEntity<String>(jsonObject, headers);
                    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
                    messageConverters.add(new FormHttpMessageConverter());
                    messageConverters.add(new StringHttpMessageConverter());
                    messageConverters.add(new MappingJackson2HttpMessageConverter());
                    template.setMessageConverters(messageConverters);
                    data = template.postForObject(url.toString(), requestGHN,String.class);
                    if (data == null) return null;
                    return data;
                case 2 :
                    HttpEntity request2;
                    request2 = new HttpEntity(headers);
                    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url.toString());
                    ResponseEntity<String> dataAhamove = template.postForEntity(builder.build().encode().toUriString(), request2, String.class);
                    if (dataAhamove == null) return null;
                    System.out.println(dataAhamove);
                    return dataAhamove.getBody();
              }
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
        return null;
    }

    public String callGet(RestBuilder rest, String urlServer) {
        StringBuilder url = new StringBuilder(urlServer  + rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        System.out.println(url.toString());
        try {
            ResponseEntity<String> dataAhamove = template.getForEntity(url.toString() , String.class);
           // if (dataAhamove.getBody() == null) return null;
            System.out.println(dataAhamove);
            return  dataAhamove.getBody();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }

    public List<Object> callGetListLocation(RestBuilder rest, HttpServletRequest request, String urlServer) {
        StringBuilder url = new StringBuilder(urlServer + rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        System.out.println(url.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token",request.getHeader("token_ghn"));
        HttpEntity entity  = new HttpEntity(headers);;
        try {
            ResponseEntity<JsonGHN> data = template.exchange(url.toString(), HttpMethod.GET,entity , JsonGHN.class);
            if (data.getBody().getData() == null) return null;
            return (List<Object>) data.getBody().getData();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }
}
