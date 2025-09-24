package com.tavi.tavi_mrs.entities.van_chuyen.ahamove;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAhamoveCreated {

    private String order_id;

    private String status;

    private String shared_link;

    private Order order;


}
