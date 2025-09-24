package com.tavi.tavi_mrs.entities.van_chuyen.ahamove;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Path {

    private Double lat;

    private Double lng;

    private String address;

    private String short_address;

    private String name;

    private String remarks;
}
