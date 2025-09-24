package com.tavi.tavi_mrs.entities.van_chuyen.ghn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfor {
   //model tra ve khi tao don hang thanh cong
    private int shop_id;

    private int client_id;

    private int converted_weight;

    private int insurance_value;

    private String pickup_time;

    private String status;

    private String leadtime;

    private String order_date ;

    private String finish_date;

}
