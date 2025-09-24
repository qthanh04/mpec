package com.tavi.tavi_mrs.entities.van_chuyen.ahamove;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order  {

    private String _id;

    private String currency;

    private Double user_main_account;

    private Double user_bonus_account;

    private Double total_pay;

    private Double distance;

    private Double duration;

    private Double distance_fee;

    private Double request_fee;

    private Double stop_fee;

    private Double vat_fee;

    private Double discount;

    private List<Path> path;

    private List<Request> requests;

    private List<Item> items;

    private Double total_fee;

    private String app;

    private String service_id;

    private String city_id;

    private String user_id;

    private String user_name;

    private Double order_time;

    private Double create_time;

    private Double delay_until;

    private String partner;

    private String polylines;

    private Double online_pay;

    private Boolean notify_package_return;

    private Double uniform_user_feedback;

    private Double pending_period;

    private Double timeout;

    private Double stoppoint_price;

    private Double special_request_price;

    private Double vat;

    private Double distance_price;

    private Double voucher_discount;

    private Double subtotal_price;

    private Double total_price;

}
