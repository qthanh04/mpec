package com.tavi.tavi_mrs.entities.van_chuyen.ahamove;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String _id;

    private Integer num;

    private String name;

    private Integer price;
}
