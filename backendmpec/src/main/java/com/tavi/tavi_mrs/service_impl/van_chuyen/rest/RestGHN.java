package com.tavi.tavi_mrs.service_impl.van_chuyen.rest;

import com.tavi.tavi_mrs.entities.van_chuyen.ghn.OrderGHN;
import com.tavi.tavi_mrs.entities.van_chuyen.ghn.OrderInforForm;
import com.tavi.tavi_mrs.entities.van_chuyen.ghn.OrderInforforms;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonGHN;
import com.tavi.tavi_mrs.entities.van_chuyen.json.RestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Slf4j
public class RestGHN {

    @Autowired
    private RestTemplate template;

//    private static final String SERVER_URL = "https://online-gateway.ghn.vn/shiip/public-api/master-data/";
      private static final String SERVER_URL = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/";


   // private static final String Order_URL = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/";
    private static final String Order_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/";

    public List<Object> callGetListLocation(RestBuilder rest, HttpServletRequest request) {
        StringBuilder url = new StringBuilder(SERVER_URL + rest.getUri() + "?");
        rest.getParams().forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        System.out.println(url.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", request.getHeader("token"));
        HttpEntity entity = new HttpEntity(headers);
        ;
        try {
            ResponseEntity<JsonGHN> data = template.exchange(url.toString(), HttpMethod.GET, entity, JsonGHN.class);
            if (data.getBody().getData() == null) return null;
            return (List<Object>) data.getBody().getData();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }

    public Object callPost(RestBuilder rest, HttpServletRequest request, OrderGHN orderGHN) {
        StringBuilder url = new StringBuilder(Order_URL + rest.getUri());
        System.out.println(url.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", request.getHeader("token"));
        headers.add("ShopId", request.getHeader("ShopId"));
        HttpEntity<OrderGHN> request2 =
                new HttpEntity<OrderGHN>(orderGHN, headers);
        try {
            ResponseEntity<JsonGHN> data = template.postForEntity(url.toString(), request2, JsonGHN.class);
            if (data.getBody().getData() == null) return null;
            System.out.println(data.getBody().getData());
            return data.getBody().getData();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }

    public Object callGet(RestBuilder rest, HttpServletRequest request, OrderInforForm orderInforForm) {
        StringBuilder url = new StringBuilder(Order_URL + rest.getUri());
        System.out.println(url.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", request.getHeader("token"));
        HttpEntity entity = new HttpEntity(orderInforForm, headers);
        try {
            ResponseEntity<JsonGHN> data = template.exchange(url.toString(), HttpMethod.POST, entity, JsonGHN.class);
            if (data.getBody().getData() == null) return null;
            return data.getBody().getData();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }

    public List<Object> callDelete(HttpServletRequest request, OrderInforforms listOrderInforform) {
        String url = "https://online-gateway.ghn.vn/shiip/public-api/v2/switch-status/cancel";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", request.getHeader("token"));
        headers.add("ShopId", request.getHeader("ShopId"));
        HttpEntity entity = new HttpEntity(listOrderInforform, headers);

        try {
            ResponseEntity<JsonGHN> data = template.exchange(url, HttpMethod.POST, entity, JsonGHN.class);
            if (data.getBody().getData() == null) return null;
            return (List<Object>) data.getBody().getData();
        } catch (Exception ex) {
            log.error("rest call " + url + " err {0}", ex);
            throw ex;
        }
    }
}
