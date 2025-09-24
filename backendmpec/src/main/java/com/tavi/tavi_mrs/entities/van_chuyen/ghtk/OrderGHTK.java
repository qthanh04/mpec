package com.tavi.tavi_mrs.entities.van_chuyen.ghtk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGHTK {
   // //model con de tao list mat hang khi tao don hang
    private String id;

    private String pick_name;

    private String pick_address;

    private String pick_province;

    private String pick_district;

    private String pick_ward;

    private String pick_tel;

    private String tel;

    private String name;

    private String address;

    private String province;

    private String district;

    private String ward;

    private String hamlet;

    private String is_freeship;

    private String pick_date;

    private double pick_money;

    private String note;

    private double value;

    private String transport;
}
