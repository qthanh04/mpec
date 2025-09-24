package com.tavi.tavi_mrs.entities.van_chuyen.ghn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGHN {
    //model khi tao don hang
    private int payment_type_id;

    private String note;

    private String required_note;

    private String return_address;

    private int return_district_id;

    private String return_ward_code;

    private String client_order_code;

    private String to_name;

    private String to_phone;

    private String to_address;

    private String to_ward_code;

    private int  to_district_id;

    private int cod_amount;

    private String content;

    private int weight;

    private int length;

    private int width;

    private int height;

    private int pick_station_id;

    private int insurance_value;

    private int service_id;

    private int service_type_id;

    private String coupon;


}
