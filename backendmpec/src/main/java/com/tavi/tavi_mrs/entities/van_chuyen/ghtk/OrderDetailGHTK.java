package com.tavi.tavi_mrs.entities.van_chuyen.ghtk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailGHTK {
    //model khi lay thong tin chi tiet don hang
    private String label_id;
    private String partner_id;
    private int status;
    private String value;
    private String insurance;
    private String ship_money;
    private String storage_day;
    private String created;
    private String pick_money;
    private String is_freeship;
    private String modified;
    private String customer_fullname;
    private String customer_tel;
    private String message;
    private String pick_date;
    private String deliver_date;
    private String address;
    private String weight;
    private String status_text;

}
