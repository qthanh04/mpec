package com.tavi.tavi_mrs.entities.van_chuyen.ahamove;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailData {

    private String token;

    private String order_id;
}
