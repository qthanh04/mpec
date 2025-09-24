package com.tavi.tavi_mrs.entities.van_chuyen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardGHN {

    private String WardCode;

    private int DistrictID;

    private String WardName;
}
