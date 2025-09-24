package com.tavi.tavi_mrs.entities.van_chuyen.ahamove;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataAhamove {

    private Integer id;

    private String token;

    private Integer order_time;

    private List<Path> path;

    private String service_id;

    private List<Request> requests;

    private List<Image> images;

    private String promo_code;

    private String remarks;

    private String payment_method;

    private List<Item> items;

}
