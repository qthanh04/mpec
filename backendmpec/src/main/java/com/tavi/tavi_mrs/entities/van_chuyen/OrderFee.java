package com.tavi.tavi_mrs.entities.van_chuyen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFee {

    private static final long serialVersionUID = 1L;

    private String orderId;

    private String note;

    private String required_note;

    private String return_phone;

    private String return_address;

    private String return_province;

    private String return_district;

    private String return_name;

    private String return_email;

    private String return_ward;

    private String to_street;

    private String to_email;

    private String to_name;

    private String to_address;

    private String to_province;

    private String to_ward;

    private String to_district;

    private String to_phone;

    private String cod_amount;

    private String hamlet;

    private String weight;

    private String height;

    private String service_type_id;//ghn : int:1,2,3,4

    private String payment_type_id;

    private Integer order_time;

    private String service_id;//aha : String :SGN-BIKE, SGN-SAMEDAY

    private String token;

    private String shopId;
}
