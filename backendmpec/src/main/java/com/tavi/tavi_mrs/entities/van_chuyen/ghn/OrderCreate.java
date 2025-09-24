package com.tavi.tavi_mrs.entities.van_chuyen.ghn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreate {
    //model tra ve khi tao don hang thanh cong
        private String order_code;
         private String sort_code;
         private String trans_type;
         private String ward_encode;
         private String district_encode;
         private Fee fee;
         private int total_fee;
         private String expected_delivery_time;
}
