package com.tavi.tavi_mrs.entities.van_chuyen.ghtk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    //model con de tao list mat hang khi tao don hang
    private String name;

    private double weight;

    private int quantity;

}
