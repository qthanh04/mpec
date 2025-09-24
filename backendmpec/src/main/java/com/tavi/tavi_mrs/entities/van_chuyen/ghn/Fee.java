package com.tavi.tavi_mrs.entities.van_chuyen.ghn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fee {

    //model phi giao hang
    private int main_service;
    private int insurance;
    private int station_do;
    private int station_pu;
    private int r2s;
    private int coupon;

}
