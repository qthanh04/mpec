package com.tavi.tavi_mrs.service_impl.van_chuyen.rest;

import com.tavi.tavi_mrs.entities.van_chuyen.ghtk.DataGHTK;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonGHTK;
import com.tavi.tavi_mrs.entities.van_chuyen.json.RestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class RestGHTK {

    @Autowired
    private RestTemplate template;

    private static final String Order_URL = "https://services.giaohangtietkiem.vn/services/";

    public Object callPostGHTK(RestBuilder rest, HttpServletRequest request, DataGHTK dataGHTK) {
        //tạo ra 1 đường dẫn url -> để gọi api(ví dụ : https://services.giaohangtietkiem.vn/services/v2/ghn/shipper)
        StringBuilder url = new StringBuilder(Order_URL + rest.getUri());
        System.out.println(url.toString());

        //tạo ra 1 header -> để truyền vào api
        HttpHeaders headers = new HttpHeaders();
        //set content type là json
        headers.setContentType(MediaType.APPLICATION_JSON);
        //set token là token của request
        headers.add("token", request.getHeader("token"));
        //set shopId là shopId của request
        HttpEntity<DataGHTK> request2 =
                new HttpEntity<DataGHTK>(dataGHTK, headers);
        try {
            //tạo ra 1 list để chứa các message converter(ví dụ : MappingJackson2HttpMessageConverter)
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            messageConverters.add(converter);
            template.setMessageConverters(messageConverters);

            //set message converter cho rest template
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));

            ResponseEntity<JsonGHTK> data = template.postForEntity(url.toString(), request2, JsonGHTK.class);
//            System.out.println(data);
            if (data.getBody() == null) return null;
//            System.out.println(data.getBody().getOrder());
            return data.getBody().getOrder();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }


    public Object callGet(RestBuilder rest, HttpServletRequest request, String lableId) {
        StringBuilder url = new StringBuilder(Order_URL + rest.getUri() + lableId);
        System.out.println(url.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", request.getHeader("token"));
        HttpEntity entity = new HttpEntity(headers);
        try {
            ResponseEntity<JsonGHTK> data = template.exchange(url.toString(), HttpMethod.GET, entity, JsonGHTK.class);
            if (data.getBody().getOrder() == null) return null;
            return data.getBody().getOrder();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }


    public Object callDelete(RestBuilder rest, HttpServletRequest request, String lableId) {
        StringBuilder url = new StringBuilder(Order_URL + rest.getUri() + lableId);
        System.out.println(url.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", request.getHeader("token"));
        HttpEntity entity = new HttpEntity(headers);
        try {
            ResponseEntity<JsonGHTK> data = template.exchange(url.toString(), HttpMethod.POST, entity, JsonGHTK.class);
            if (data.getBody().getSuccess() == null) return null;
            return data.getBody();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }


}
