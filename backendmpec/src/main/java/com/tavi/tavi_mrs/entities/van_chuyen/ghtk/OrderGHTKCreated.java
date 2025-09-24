package com.tavi.tavi_mrs.entities.van_chuyen.ghtk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGHTKCreated {
    //model tra ve khi tao don hang
    private String partner_id;

    private String label;

    private String area;

    private String fee;

    private String status_id;

    private String insurance_fee;

    private String estimated_pick_time;

    private String estimated_deliver_time;

    private int tracking_id;

    private String sorting_code;

    private List<Product> products;

}
