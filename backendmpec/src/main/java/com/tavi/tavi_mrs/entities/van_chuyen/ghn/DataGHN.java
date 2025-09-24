package com.tavi.tavi_mrs.entities.van_chuyen.ghn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGHN {

    //model khi post don hang
    private int orderId;

    private String maHoaDon;

    private String to_province;

    private String return_province;

    private String to_district;

    private String to_ward;

    private String to_address;//so nha, ten duong

    private String return_district;

    private String return_ward;

    private String return_address;
    //

    private int payment_type_id;

    private String note;

    private String required_note;

    private String client_order_code;

    private String to_name;

    private String to_phone;

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
